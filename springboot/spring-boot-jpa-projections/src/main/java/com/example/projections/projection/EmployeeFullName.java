package com.example.projections.projection;

import org.springframework.beans.factory.annotation.Value;

// Scenario 2: Open projection (SpEL). Loads the whole entity.
public interface EmployeeFullName {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    Double getSalary();
}
