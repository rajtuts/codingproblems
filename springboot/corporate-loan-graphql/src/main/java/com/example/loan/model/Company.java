package com.example.loan.model;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String industry;
    private Double annualRevenue;
    private String creditRating;

    public Company() {}

    public Company(String name, String industry, Double annualRevenue, String creditRating) {
        this.name = name;
        this.industry = industry;
        this.annualRevenue = annualRevenue;
        this.creditRating = creditRating;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getIndustry() { return industry; }
    public Double getAnnualRevenue() { return annualRevenue; }
    public String getCreditRating() { return creditRating; }
}
