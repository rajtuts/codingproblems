package com.example.loan.model;

import jakarta.persistence.*;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanType;
    private Double principalAmount;
    private Double interestRate;   // annual, in percent
    private Integer tenureMonths;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public Loan() {}

    public Loan(String loanType, Double principalAmount, Double interestRate,
                Integer tenureMonths, LoanStatus status, Company company) {
        this.loanType = loanType;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.status = status;
        this.company = company;
    }

    public Long getId() { return id; }
    public String getLoanType() { return loanType; }
    public Double getPrincipalAmount() { return principalAmount; }
    public Double getInterestRate() { return interestRate; }
    public Integer getTenureMonths() { return tenureMonths; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
    public Company getCompany() { return company; }
}
