package com.example.dynamicforms.dto;

import java.time.Instant;
import java.util.Map;

public record SubmissionDto(Long id, String formCode, Instant submittedAt, Map<String, Object> values) {}
