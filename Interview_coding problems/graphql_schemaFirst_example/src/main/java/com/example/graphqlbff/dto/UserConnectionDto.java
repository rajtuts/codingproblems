package com.example.graphqlbff.dto;

import java.util.List;

public record UserConnectionDto(List<UserEdgeDto> edges, PageInfoDto pageInfo) {}
