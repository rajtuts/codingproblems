package com.example.criteriademo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;          // nullable on purpose (IS NULL demo)
    private int age;
    private double salary;
    private boolean active;
    private LocalDate hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>();

    protected Employee() {}

    public Employee(String firstName, String lastName, String email, String phone, int age,
                    double salary, boolean active, LocalDate hireDate, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.salary = salary;
        this.active = active;
        this.hireDate = hireDate;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }
    public Set<Project> getProjects() { return projects; }

    // Deliberately does not touch lazy associations
    @Override public String toString() {
        return "Employee[" + id + ", " + firstName + " " + lastName + ", age=" + age
                + ", salary=" + salary + ", active=" + active + "]";
    }
}
