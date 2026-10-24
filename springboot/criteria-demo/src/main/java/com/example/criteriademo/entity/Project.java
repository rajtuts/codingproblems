package com.example.criteriademo.entity;

import jakarta.persistence.*;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double budget;

    protected Project() {}

    public Project(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
}
