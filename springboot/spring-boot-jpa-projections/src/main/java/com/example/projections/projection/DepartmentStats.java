package com.example.projections.projection;

// Scenario 5: Aggregation / GROUP BY
public interface DepartmentStats {
    String getDepartmentName();
    Long getEmployeeCount();
    Double getAvgSalary();
    Double getMaxSalary();
}
