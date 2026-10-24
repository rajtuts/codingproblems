package com.example.metaplatform;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MetadataService {
    private final MetadataRepository metadataRepository;
    private final RecordRepository recordRepository;
    private final ObjectMapper mapper;

    public MetadataService(MetadataRepository metadataRepository,
                           RecordRepository recordRepository, ObjectMapper mapper) {
        this.metadataRepository = metadataRepository;
        this.recordRepository = recordRepository;
        this.mapper = mapper;
    }

    @Transactional
    public MetadataEntity createDefinition(String tenantId, String name, JsonNode schema) {
        validateDefinition(name, schema);
        if (metadataRepository.findByTenantIdAndName(tenantId, name).isPresent()) {
            throw new IllegalArgumentException("Definition already exists for this tenant: " + name);
        }
        try {
            return metadataRepository.save(new MetadataEntity(tenantId, name, mapper.writeValueAsString(schema)));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid schema JSON", e);
        }
    }

    @Transactional(readOnly = true)
    public MetadataEntity getDefinition(String tenantId, String name) {
        return metadataRepository.findByTenantIdAndName(tenantId, name)
            .orElseThrow(() -> new NoSuchElementException("Definition not found: " + name));
    }

    @Transactional
    public RecordEntity createRecord(String tenantId, String entityName, JsonNode data) {
        MetadataEntity definition = getDefinition(tenantId, entityName);
        JsonNode schema;
        try {
            schema = mapper.readTree(definition.getSchemaJson());
        } catch (Exception e) {
            throw new IllegalStateException("Stored schema is invalid", e);
        }
        validateRecord(schema, data);
        try {
            return recordRepository.save(new RecordEntity(tenantId, entityName, mapper.writeValueAsString(data)));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid record JSON", e);
        }
    }

    @Transactional(readOnly = true)
    public List<RecordEntity> listRecords(String tenantId, String entityName) {
        getDefinition(tenantId, entityName); // Don't expose records for undefined entities.
        return recordRepository.findTop100ByTenantIdAndEntityNameOrderByIdDesc(tenantId, entityName);
    }

    private void validateDefinition(String name, JsonNode schema) {
        if (name == null || !name.matches("[A-Za-z][A-Za-z0-9_]{0,99}")) {
            throw new IllegalArgumentException("Name must start with a letter and contain only letters, digits, underscores");
        }
        if (schema == null || !schema.isObject() || !schema.path("fields").isArray()) {
            throw new IllegalArgumentException("Schema must be an object with a 'fields' array");
        }
        Set<String> names = new HashSet<>();
        for (JsonNode field : schema.path("fields")) {
            String fieldName = field.path("name").asText("");
            String type = field.path("type").asText("");
            if (!fieldName.matches("[A-Za-z][A-Za-z0-9_]{0,99}") || !names.add(fieldName)) {
                throw new IllegalArgumentException("Field names must be valid and unique");
            }
            if (!Set.of("string", "number", "boolean").contains(type)) {
                throw new IllegalArgumentException("Supported field types: string, number, boolean");
            }
        }
    }

    private void validateRecord(JsonNode schema, JsonNode data) {
        if (data == null || !data.isObject()) throw new IllegalArgumentException("Record data must be a JSON object");
        Map<String, String> allowed = new HashMap<>();
        for (JsonNode field : schema.path("fields")) {
            allowed.put(field.path("name").asText(), field.path("type").asText());
        }
        data.fieldNames().forEachRemaining(fieldName -> {
            if (!allowed.containsKey(fieldName)) {
                throw new IllegalArgumentException("Unknown field: " + fieldName);
            }
        });
        for (Map.Entry<String, String> field : allowed.entrySet()) {
            JsonNode value = data.get(field.getKey());
            if (value == null || value.isNull()) continue; // Required rules can be added to metadata.
            boolean valid = switch (field.getValue()) {
                case "string" -> value.isTextual();
                case "number" -> value.isNumber();
                case "boolean" -> value.isBoolean();
                default -> false;
            };
            if (!valid) throw new IllegalArgumentException("Field '" + field.getKey() + "' must be " + field.getValue());
        }
    }
}
