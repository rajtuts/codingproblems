package com.example.dynamicforms.dto;

public record FieldDto(
        String key, String label, String type, boolean required,
        boolean readOnly, String optionsJson, String defaultValue, int sortOrder) {}
