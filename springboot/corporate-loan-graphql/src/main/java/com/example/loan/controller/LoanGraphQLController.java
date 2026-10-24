package com.example.loan.controller;

import com.example.loan.model.Company;
import com.example.loan.model.Loan;
import com.example.loan.model.LoanStatus;
import com.example.loan.repository.CompanyRepository;
import com.example.loan.repository.LoanRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LoanGraphQLController {

    private final CompanyRepository companyRepository;
    private final LoanRepository loanRepository;

    public LoanGraphQLController(CompanyRepository companyRepository, LoanRepository loanRepository) {
        this.companyRepository = companyRepository;
        this.loanRepository = loanRepository;
    }

    // ---------- Input types (match the GraphQL input types) ----------
    public record CompanyInput(String name, String industry, Double annualRevenue, String creditRating) {}

    public record LoanInput(Long companyId, String loanType, Double principalAmount,
                            Double interestRate, Integer tenureMonths) {}

    // ---------- Queries ----------
    @QueryMapping
    public List<Company> companies() {
        return companyRepository.findAll();
    }

    @QueryMapping
    public Company companyById(@Argument Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Loan> loans() {
        return loanRepository.findAll();
    }

    @QueryMapping
    public Loan loanById(@Argument Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Loan> loansByStatus(@Argument LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    // ---------- Mutations ----------
    @MutationMapping
    public Company addCompany(@Argument CompanyInput input) {
        return companyRepository.save(new Company(
                input.name(), input.industry(), input.annualRevenue(), input.creditRating()));
    }

    @MutationMapping
    public Loan applyForLoan(@Argument LoanInput input) {
        Company company = companyRepository.findById(input.companyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + input.companyId()));
        return loanRepository.save(new Loan(
                input.loanType(), input.principalAmount(), input.interestRate(),
                input.tenureMonths(), LoanStatus.APPLIED, company));
    }

    @MutationMapping
    public Loan updateLoanStatus(@Argument Long id, @Argument LoanStatus status) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + id));
        loan.setStatus(status);
        return loanRepository.save(loan);
    }

    // ---------- Field resolvers ----------
    // Company.loans -> fetched only when the client asks for it
    @SchemaMapping(typeName = "Company", field = "loans")
    public List<Loan> loansOfCompany(Company company) {
        return loanRepository.findByCompanyId(company.getId());
    }

    // Loan.monthlyEmi -> computed field, not stored in DB
    @SchemaMapping(typeName = "Loan", field = "monthlyEmi")
    public Double monthlyEmi(Loan loan) {
        double r = loan.getInterestRate() / 12 / 100;
        int n = loan.getTenureMonths();
        double p = loan.getPrincipalAmount();
        if (r == 0) return p / n;
        double factor = Math.pow(1 + r, n);
        double emi = p * r * factor / (factor - 1);
        return Math.round(emi * 100.0) / 100.0;
    }
}
