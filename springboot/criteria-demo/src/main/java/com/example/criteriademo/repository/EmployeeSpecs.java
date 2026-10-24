package com.example.criteriademo.repository;

import com.example.criteriademo.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

/** Reusable, composable Criteria fragments for Spring Data's Specification API. */
public final class EmployeeSpecs {
    private EmployeeSpecs() {}

    public static Specification<Employee> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<Employee> salaryAtLeast(double min) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Double>get("salary"), min);
    }

    public static Specification<Employee> inDepartment(String name) {
        return (root, query, cb) -> cb.equal(root.join("department").get("name"), name);
    }

    public static Specification<Employee> lastNameContains(String text) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + text.toLowerCase() + "%");
    }
}
