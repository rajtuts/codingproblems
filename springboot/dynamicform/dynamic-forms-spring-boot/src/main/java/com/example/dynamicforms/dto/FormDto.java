package com.example.dynamicforms.dto;

import java.util.List;

public record FormDto(Long id, String code, String title, List<FieldDto> fields) {}
