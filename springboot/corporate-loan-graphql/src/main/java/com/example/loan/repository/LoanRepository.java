package com.example.loan.repository;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus status);
    List<Loan> findByCompanyId(Long companyId);
}
