INSERT INTO company (name, industry, annual_revenue, credit_rating) VALUES ('Acme Manufacturing', 'Manufacturing', 50000000, 'AA');
INSERT INTO company (name, industry, annual_revenue, credit_rating) VALUES ('BrightSoft Technologies', 'IT Services', 12000000, 'A');
INSERT INTO company (name, industry, annual_revenue, credit_rating) VALUES ('GreenLeaf Logistics', 'Logistics', 8000000, 'BBB');

INSERT INTO loan (loan_type, principal_amount, interest_rate, tenure_months, status, company_id) VALUES ('TERM_LOAN', 5000000, 9.5, 60, 'APPROVED', 1);
INSERT INTO loan (loan_type, principal_amount, interest_rate, tenure_months, status, company_id) VALUES ('WORKING_CAPITAL', 1500000, 11.0, 12, 'APPLIED', 2);
INSERT INTO loan (loan_type, principal_amount, interest_rate, tenure_months, status, company_id) VALUES ('EQUIPMENT_FINANCE', 2500000, 10.25, 36, 'DISBURSED', 3);
INSERT INTO loan (loan_type, principal_amount, interest_rate, tenure_months, status, company_id) VALUES ('WORKING_CAPITAL', 800000, 12.5, 12, 'REJECTED', 3);
