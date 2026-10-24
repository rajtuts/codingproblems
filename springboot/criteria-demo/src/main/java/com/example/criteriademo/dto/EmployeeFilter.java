package com.example.criteriademo.dto;

/** All fields optional: only the ones supplied become predicates. */
public record EmployeeFilter(String name, String department, Double minSalary, Double maxSalary, Boolean active) {}
