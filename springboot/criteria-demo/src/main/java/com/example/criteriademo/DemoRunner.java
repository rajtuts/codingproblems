package com.example.criteriademo;

import com.example.criteriademo.dao.EmployeeCriteriaDao;
import com.example.criteriademo.dto.EmployeeFilter;
import com.example.criteriademo.entity.Employee;
import com.example.criteriademo.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.example.criteriademo.repository.EmployeeSpecs.*;

/** Runs every scenario on startup and prints the results. */
@Component
@Order(2)
public class DemoRunner implements CommandLineRunner {

    private final EmployeeCriteriaDao dao;
    private final EmployeeRepository repo;

    public DemoRunner(EmployeeCriteriaDao dao, EmployeeRepository repo) {
        this.dao = dao;
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        section("1. Select all");                       dao.findAll().forEach(System.out::println);
        section("2. lastName = Smith");                 dao.findByLastName("Smith").forEach(System.out::println);
        section("3a. AND: active && salary >= 90000");  dao.findActiveWithMinSalary(90000).forEach(System.out::println);
        section("3b. OR: firstName/lastName = 'smith'"); dao.findByFirstNameOrLastName("smith").forEach(System.out::println);
        section("4a. LIKE firstName 'a%'");             dao.findByFirstNamePrefix("a").forEach(System.out::println);
        section("4b. BETWEEN age 25 and 35");           dao.findByAgeBetween(25, 35).forEach(System.out::println);
        section("4c. IN lastName (Jones, Lee)");        dao.findByLastNameIn(List.of("Jones", "Lee")).forEach(System.out::println);
        section("4d. phone IS NULL");                   dao.findWithoutPhone().forEach(System.out::println);
        section("4e. hired after 2020-01-01");          dao.findHiredAfter(LocalDate.of(2020, 1, 1)).forEach(System.out::println);
        section("5. Sorted salary desc, lastName asc"); dao.findAllSorted().forEach(System.out::println);

        section("6. Page 1 (size 3)");
        Page<Employee> page = dao.findPage(1, 3);
        page.getContent().forEach(System.out::println);
        System.out.println("total=" + page.getTotalElements() + ", pages=" + page.getTotalPages());

        section("7a. Join: department = Engineering");  dao.findByDepartmentName("Engineering").forEach(System.out::println);
        section("7b. Many-to-many: project = Gemini");  dao.findByProjectName("Gemini").forEach(System.out::println);
        section("7c. Fetch join (1 query)");            dao.findAllWithDepartmentFetched().forEach(System.out::println);
        section("8. DTO projection");                   dao.findAllAsDto().forEach(System.out::println);
        section("9. Aggregates");                       System.out.println(dao.salaryStats());
        section("10. Group by + having (>= 2 people)"); dao.statsByDepartment(2).forEach(System.out::println);
        section("11a. Salary > average (subquery)");    dao.findAboveAverageSalary().forEach(System.out::println);
        section("11b. Depts with earner > 125000 (EXISTS)"); dao.findDepartmentsWithHighEarners(125000).forEach(System.out::println);
        section("12. CASE expression");                 dao.salaryBands().forEach(System.out::println);

        section("13. Dynamic search: name='a', Sales, minSalary=50000");
        dao.search(new EmployeeFilter("a", "Sales", 50000.0, null, null)).forEach(System.out::println);

        section("15. Specifications: active AND salary>=80000 AND Engineering");
        Specification<Employee> spec = Specification.where(isActive())
                .and(salaryAtLeast(80000))
                .and(inDepartment("Engineering"));
        repo.findAll(spec, Sort.by("salary").descending()).forEach(System.out::println);

        section("15b. Specifications: lastName contains 'smi'");
        repo.findAll(lastNameContains("smi")).forEach(System.out::println);

        // Bulk operations last because they change data
        section("14a. CriteriaUpdate: +10% for salary < 60000");
        System.out.println("rows updated: " + dao.raiseSalaryBelow(60000, 10));
        dao.findAllSorted().forEach(System.out::println);

        section("14b. CriteriaDelete: inactive employees");
        System.out.println("rows deleted: " + dao.deleteInactive());
        System.out.println("remaining: " + dao.findAll().size());
    }

    private static void section(String title) {
        System.out.println("\n===== " + title + " =====");
    }
}
