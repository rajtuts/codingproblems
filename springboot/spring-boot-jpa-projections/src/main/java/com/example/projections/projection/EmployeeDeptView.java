package com.example.projections.projection;

// Scenario 4: @Query with aliases and join
public interface EmployeeDeptView {
    String getEmployeeName();
    String getDepartmentName();
    Double getSalary();
}
