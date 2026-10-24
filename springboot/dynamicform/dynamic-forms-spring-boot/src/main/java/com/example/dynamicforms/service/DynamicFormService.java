package com.example.dynamicforms.service;

import com.example.dynamicforms.dto.*;
import com.example.dynamicforms.model.*;
import com.example.dynamicforms.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DynamicFormService {
    private final FormDefinitionRepository forms;
    private final FormSubmissionRepository submissions;
    private final ObjectMapper mapper;

    public DynamicFormService(FormDefinitionRepository forms,
                              FormSubmissionRepository submissions,
                              ObjectMapper mapper) {
        this.forms = forms;
        this.submissions = submissions;
        this.mapper = mapper;
    }

    @Transactional
    public FormDto getForm(String code) {
        FormDefinition form = getDefinition(code);
        List<FieldDto> fields = form.getFields().stream()
                .sorted(Comparator.comparingInt(FormField::getSortOrder))
                .map(f -> new FieldDto(f.getFieldKey(), f.getLabel(), f.getType(),
                        f.isRequired(), f.isReadOnly(), f.getOptionsJson(),
                        f.getDefaultValue(), f.getSortOrder()))
                .toList();
        return new FormDto(form.getId(), form.getCode(), form.getTitle(), fields);
    }

    @Transactional
    public SubmissionDto submit(String code, SubmissionRequest request) {
        FormDefinition form = getDefinition(code);
        Map<String, Object> incoming = request == null || request.values() == null
                ? Collections.emptyMap() : request.values();

        Set<String> allowedKeys = form.getFields().stream()
                .map(FormField::getFieldKey).collect(Collectors.toSet());
        for (String key : incoming.keySet()) {
            if (!allowedKeys.contains(key)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown field: " + key);
            }
        }

        FormSubmission submission = new FormSubmission();
        submission.setForm(form);

        Map<String, Object> savedValues = new LinkedHashMap<>();
        for (FormField field : form.getFields()) {
            Object value;

            // Never trust a client-supplied value for a read-only field.
            if (field.isReadOnly()) {
                value = field.getDefaultValue();
            } else {
                value = incoming.get(field.getFieldKey());
            }

            validateField(field, value);

            if (value != null) {
                FormFieldValue fieldValue = new FormFieldValue();
                fieldValue.setSubmission(submission);
                fieldValue.setFieldKey(field.getFieldKey());
                fieldValue.setValueJson(toJson(value));
                submission.getValues().add(fieldValue);
                savedValues.put(field.getFieldKey(), value);
            }
        }

        FormSubmission saved = submissions.save(submission);
        return new SubmissionDto(saved.getId(), form.getCode(), saved.getSubmittedAt(), savedValues);
    }

    @Transactional
    public SubmissionDto getSubmission(Long id) {
        FormSubmission submission = submissions.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found"));
        Map<String, Object> values = new LinkedHashMap<>();
        for (FormFieldValue value : submission.getValues()) {
            values.put(value.getFieldKey(), fromJson(value.getValueJson()));
        }
        return new SubmissionDto(submission.getId(), submission.getForm().getCode(),
                submission.getSubmittedAt(), values);
    }

    @Transactional
    public List<SubmissionDto> listSubmissions(String code) {
        getDefinition(code); // ensure form exists
        return submissions.findByForm_CodeOrderBySubmittedAtDesc(code).stream()
                .map(s -> getSubmission(s.getId()))
                .toList();
    }

    private FormDefinition getDefinition(String code) {
        return forms.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found: " + code));
    }

    private void validateField(FormField field, Object value) {
        if (value == null || (value instanceof String s && s.isBlank())) {
            if (field.isRequired()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Field '" + field.getFieldKey() + "' is required");
            }
            return;
        }

        String type = field.getType().toUpperCase(Locale.ROOT);
        switch (type) {
            case "DROPDOWN", "RADIO" -> {
                List<String> options = parseOptions(field.getOptionsJson());
                if (!options.contains(String.valueOf(value))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Invalid option for field '" + field.getFieldKey() + "'");
                }
            }
            case "CHECKBOX" -> {
                if (!(value instanceof Boolean)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Field '" + field.getFieldKey() + "' must be true or false");
                }
            }
            case "NUMBER" -> {
                if (!(value instanceof Number)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Field '" + field.getFieldKey() + "' must be numeric");
                }
            }
            case "EMAIL" -> {
                String email = String.valueOf(value);
                if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Field '" + field.getFieldKey() + "' must be a valid email");
                }
            }
            default -> { /* Add type-specific validation here as needed. */ }
        }
    }

    private List<String> parseOptions(String json) {
        if (json == null || json.isBlank()) return List.of();
        try {
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid field options metadata");
        }
    }

    private String toJson(Object value) {
        try { return mapper.writeValueAsString(value); }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported field value");
        }
    }

    private Object fromJson(String value) {
        try { return mapper.readValue(value, Object.class); }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid stored field value");
        }
    }
}
