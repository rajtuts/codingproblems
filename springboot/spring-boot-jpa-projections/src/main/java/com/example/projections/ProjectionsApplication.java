package com.example.projections;

import com.example.projections.dto.EmployeeDto;
import com.example.projections.projection.EmployeeSummary;
import com.example.projections.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@SpringBootApplication
public class ProjectionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectionsApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(EmployeeRepository repo) {
        return args -> {
            System.out.println("\n--- 1. Closed projection ---");
            repo.findByActiveTrue().forEach(e ->
                    System.out.println(e.getFirstName() + " " + e.getLastName() + " <" + e.getEmail() + ">"));

            System.out.println("\n--- 1b. Closed projection, filtered and sorted ---");
            repo.findByDepartmentNameOrderBySalaryDesc("Engineering")
                    .forEach(e -> System.out.println(e.getFirstName()));

            System.out.println("\n--- 2. Open projection (SpEL) ---");
            repo.findBySalaryGreaterThan(80000.0).forEach(e ->
                    System.out.println(e.getFullName() + " earns " + e.getSalary()));

            System.out.println("\n--- 3. Nested projection ---");
            repo.findByDepartmentLocation("Hyderabad").forEach(e ->
                    System.out.println(e.getFirstName() + " works in " + e.getDepartment().getName()
                            + " (" + e.getDepartment().getLocation() + ")"));

            System.out.println("\n--- 4. @Query interface projection ---");
            repo.findActiveEmployeeDeptView().forEach(v ->
                    System.out.println(v.getEmployeeName() + " | " + v.getDepartmentName() + " | " + v.getSalary()));

            System.out.println("\n--- 5. Aggregation ---");
            repo.getDepartmentStats().forEach(s ->
                    System.out.println(s.getDepartmentName() + ": count=" + s.getEmployeeCount()
                            + ", avg=" + s.getAvgSalary() + ", max=" + s.getMaxSalary()));

            System.out.println("\n--- 6. DTO (record) projection ---");
            repo.findByHireDateAfter(LocalDate.of(2020, 1, 1)).forEach(System.out::println);

            System.out.println("\n--- 7. JPQL constructor expression ---");
            repo.findContactsByDepartment("Engineering").forEach(System.out::println);

            System.out.println("\n--- 8. Dynamic projection ---");
            repo.findByDepartmentName("Engineering", EmployeeSummary.class)
                    .forEach(e -> System.out.println("Summary: " + e.getEmail()));
            repo.findByDepartmentName("Engineering", EmployeeDto.class)
                    .forEach(e -> System.out.println("DTO: " + e));

            System.out.println("\n--- 9. Native query projection ---");
            repo.findHighEarnersNative(90000).forEach(e ->
                    System.out.println(e.getFirstName() + " " + e.getLastName()));

            System.out.println("\n--- 10. Paged projection ---");
            repo.findByActiveTrue(PageRequest.of(0, 2, Sort.by("salary").descending()))
                    .forEach(e -> System.out.println(e.getFirstName()));

            System.out.println("\n--- 11. Scalar projection ---");
            System.out.println(repo.findActiveEmails());
        };
    }
}
