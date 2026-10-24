package com.example.projections.projection;

// Scenario 3: Nested projection
public interface EmployeeWithDepartment {
    String getFirstName();
    DepartmentInfo getDepartment();

    interface DepartmentInfo {
        String getName();
        String getLocation();
    }
}
