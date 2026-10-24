package com.example.projections.repository;

import com.example.projections.dto.*;
import com.example.projections.entity.Employee;
import com.example.projections.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 1. Closed interface projection via derived query
    List<EmployeeSummary> findByActiveTrue();

    // 1b. Filtered and sorted
    List<EmployeeSummary> findByDepartmentNameOrderBySalaryDesc(String departmentName);

    // 1c. Single result
    Optional<EmployeeSummary> findByEmail(String email);

    // 2. Open projection
    List<EmployeeFullName> findBySalaryGreaterThan(Double salary);

    // 3. Nested projection
    List<EmployeeWithDepartment> findByDepartmentLocation(String location);

    // 4. Interface projection with @Query + aliases
    @Query("""
           select concat(e.firstName, ' ', e.lastName) as employeeName,
                  d.name as departmentName,
                  e.salary as salary
           from Employee e join e.department d
           where e.active = true
           """)
    List<EmployeeDeptView> findActiveEmployeeDeptView();

    // 5. Aggregation
    @Query("""
           select d.name as departmentName,
                  count(e) as employeeCount,
                  avg(e.salary) as avgSalary,
                  max(e.salary) as maxSalary
           from Employee e join e.department d
           group by d.name
           """)
    List<DepartmentStats> getDepartmentStats();

    // 6. Class-based (DTO) projection via derived query
    List<EmployeeDto> findByHireDateAfter(LocalDate date);

    // 7. JPQL constructor expression
    @Query("""
           select new com.example.projections.dto.EmployeeContactDto(
                  concat(e.firstName, ' ', e.lastName), e.email, d.name)
           from Employee e join e.department d
           where d.name = :dept
           """)
    List<EmployeeContactDto> findContactsByDepartment(@Param("dept") String dept);

    // 8. Dynamic projection
    <T> List<T> findByDepartmentName(String departmentName, Class<T> type);

    // 9. Native query + interface projection
    @Query(value = """
            select e.first_name as firstName,
                   e.last_name  as lastName,
                   e.email      as email
            from employee e
            where e.salary >= :minSalary
            """, nativeQuery = true)
    List<EmployeeSummary> findHighEarnersNative(@Param("minSalary") double minSalary);

    // 10. Pagination + projection
    Page<EmployeeSummary> findByActiveTrue(Pageable pageable);

    // 11. Scalar projection
    @Query("select e.email from Employee e where e.active = true")
    List<String> findActiveEmails();
}
