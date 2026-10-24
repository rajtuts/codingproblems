package com.example.dynamicforms.config;

import com.example.dynamicforms.model.FormDefinition;
import com.example.dynamicforms.model.FormField;
import com.example.dynamicforms.repository.FormDefinitionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
    @Bean
    CommandLineRunner seedDemoForm(FormDefinitionRepository repository) {
        return args -> {
            if (repository.findByCode("employee").isPresent()) return;

            FormDefinition form = new FormDefinition();
            form.setCode("employee");
            form.setTitle("Employee Registration");

            add(form, "employeeId", "Employee ID", "TEXT", false, true, null, "EMP-1001", 1);
            add(form, "fullName", "Full Name", "TEXT", true, false, null, null, 2);
            add(form, "email", "Email Address", "EMAIL", true, false, null, null, 3);
            add(form, "department", "Department", "DROPDOWN", true, false,
                    "[\"Engineering\",\"Finance\",\"HR\"]", null, 4);
            add(form, "employmentType", "Employment Type", "RADIO", false, false,
                    "[\"Permanent\",\"Contract\"]", null, 5);
            add(form, "remoteWorker", "Remote Worker", "CHECKBOX", false, false, null, null, 6);
            add(form, "yearsExperience", "Years of Experience", "NUMBER", false, false, null, null, 7);

            repository.save(form);
        };
    }

    private static void add(FormDefinition form, String key, String label, String type,
                            boolean required, boolean readOnly, String options,
                            String defaultValue, int order) {
        FormField field = new FormField();
        field.setForm(form);
        field.setFieldKey(key);
        field.setLabel(label);
        field.setType(type);
        field.setRequired(required);
        field.setReadOnly(readOnly);
        field.setOptionsJson(options);
        field.setDefaultValue(defaultValue);
        field.setSortOrder(order);
        form.getFields().add(field);
    }
}
