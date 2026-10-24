package com.example.criteriademo.dao;

import com.example.criteriademo.dto.DeptStats;
import com.example.criteriademo.dto.EmployeeDto;
import com.example.criteriademo.dto.EmployeeFilter;
import com.example.criteriademo.entity.Department;
import com.example.criteriademo.entity.Employee;
import com.example.criteriademo.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.util.StringUtils.hasText;

/** Every method below is one self-contained JPA Criteria API scenario. */
@Repository
@Transactional(readOnly = true)
public class EmployeeCriteriaDao {

    @PersistenceContext
    private EntityManager em;

    // ------------------------------------------------------------------
    // 1. SELECT * FROM employee
    // ------------------------------------------------------------------
    public List<Employee> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e);
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 2. Simple WHERE (equality)
    // ------------------------------------------------------------------
    public List<Employee> findByLastName(String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.equal(e.get("lastName"), lastName));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 3. AND / OR
    // ------------------------------------------------------------------
    public List<Employee> findActiveWithMinSalary(double minSalary) {          // AND
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.and(
                cb.isTrue(e.get("active")),
                cb.greaterThanOrEqualTo(e.<Double>get("salary"), minSalary)));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findByFirstNameOrLastName(String text) {              // OR
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.or(
                cb.equal(cb.lower(e.get("firstName")), text.toLowerCase()),
                cb.equal(cb.lower(e.get("lastName")), text.toLowerCase())));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 4. Operators: LIKE, BETWEEN, IN, IS NULL, date comparison
    // ------------------------------------------------------------------
    public List<Employee> findByFirstNamePrefix(String prefix) {                // LIKE
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.like(cb.lower(e.get("firstName")), prefix.toLowerCase() + "%"));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findByAgeBetween(int min, int max) {                  // BETWEEN
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.between(e.<Integer>get("age"), min, max));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findByLastNameIn(Collection<String> names) {          // IN
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(e.get("lastName").in(names));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findWithoutPhone() {                                  // IS NULL
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.isNull(e.get("phone")));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findHiredAfter(java.time.LocalDate date) {            // date compare
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).where(cb.greaterThan(e.<java.time.LocalDate>get("hireDate"), date));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 5. ORDER BY (multiple keys)
    // ------------------------------------------------------------------
    public List<Employee> findAllSorted() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).orderBy(cb.desc(e.get("salary")), cb.asc(e.get("lastName")));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 6. Pagination (data query + count query)
    // ------------------------------------------------------------------
    public Page<Employee> findPage(int page, int size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e).orderBy(cb.asc(e.get("lastName")), cb.asc(e.get("id")));
        List<Employee> content = em.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Employee.class)));
        long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    // ------------------------------------------------------------------
    // 7. Joins
    // ------------------------------------------------------------------
    public List<Employee> findByDepartmentName(String deptName) {               // INNER JOIN many-to-one
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        Join<Employee, Department> d = e.join("department");
        cq.select(e).where(cb.equal(d.get("name"), deptName));
        return em.createQuery(cq).getResultList();
    }

    public List<Employee> findByProjectName(String projectName) {               // many-to-many join
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        Join<Employee, Project> p = e.join("projects");
        cq.select(e).distinct(true).where(cb.equal(p.get("name"), projectName));
        return em.createQuery(cq).getResultList();
    }

    /** LEFT JOIN FETCH: loads employee + department in one query (avoids N+1). */
    public List<Employee> findAllWithDepartmentFetched() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);
        e.fetch("department", JoinType.LEFT);
        cq.select(e);
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 8. Projection to a DTO (constructor expression) with a join
    // ------------------------------------------------------------------
    public List<EmployeeDto> findAllAsDto() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeDto> cq = cb.createQuery(EmployeeDto.class);
        Root<Employee> e = cq.from(Employee.class);
        Join<Employee, Department> d = e.join("department", JoinType.LEFT);
        cq.select(cb.construct(EmployeeDto.class,
                e.get("id"), e.get("firstName"), e.get("lastName"), e.get("salary"), d.get("name")));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 9. Aggregates (COUNT / AVG / MAX / MIN / SUM) via Tuple
    // ------------------------------------------------------------------
    public Map<String, Object> salaryStats() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Employee> e = cq.from(Employee.class);
        Expression<Double> salary = e.get("salary");
        cq.multiselect(
                cb.count(e).alias("total"),
                cb.avg(salary).alias("avg"),
                cb.max(salary).alias("max"),
                cb.min(salary).alias("min"),
                cb.sum(salary).alias("sum"));
        Tuple t = em.createQuery(cq).getSingleResult();

        Map<String, Object> result = new LinkedHashMap<>();
        for (String key : List.of("total", "avg", "max", "min", "sum")) {
            result.put(key, t.get(key));
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 10. GROUP BY + HAVING + ORDER BY into a DTO
    // ------------------------------------------------------------------
    public List<DeptStats> statsByDepartment(long minHeadcount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeptStats> cq = cb.createQuery(DeptStats.class);
        Root<Employee> e = cq.from(Employee.class);
        Join<Employee, Department> d = e.join("department");
        Expression<Double> avgSalary = cb.avg(e.<Double>get("salary"));

        cq.select(cb.construct(DeptStats.class, d.get("name"), cb.count(e), avgSalary))
          .groupBy(d.get("name"))
          .having(cb.ge(cb.count(e), minHeadcount))
          .orderBy(cb.desc(avgSalary));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 11. Subqueries
    // ------------------------------------------------------------------
    /** salary > (SELECT AVG(salary) FROM employee) : scalar subquery */
    public List<Employee> findAboveAverageSalary() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);

        Subquery<Double> sub = cq.subquery(Double.class);
        Root<Employee> s = sub.from(Employee.class);
        sub.select(cb.avg(s.<Double>get("salary")));

        cq.select(e).where(cb.greaterThan(e.<Double>get("salary"), sub));
        return em.createQuery(cq).getResultList();
    }

    /** Departments having at least one employee earning more than threshold : correlated EXISTS */
    public List<Department> findDepartmentsWithHighEarners(double threshold) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> d = cq.from(Department.class);

        Subquery<Long> sub = cq.subquery(Long.class);
        Root<Employee> e = sub.from(Employee.class);
        sub.select(e.<Long>get("id")).where(
                cb.equal(e.get("department"), d),                 // correlation
                cb.greaterThan(e.<Double>get("salary"), threshold));

        cq.select(d).where(cb.exists(sub));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 12. CASE expression + CONCAT
    // ------------------------------------------------------------------
    public List<String> salaryBands() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Employee> e = cq.from(Employee.class);

        Expression<String> fullName = cb.concat(cb.concat(e.<String>get("firstName"), " "), e.<String>get("lastName"));
        Expression<String> band = cb.<String>selectCase()
                .when(cb.ge(e.<Double>get("salary"), 100000), "HIGH")
                .when(cb.ge(e.<Double>get("salary"), 70000), "MEDIUM")
                .otherwise("LOW");

        cq.multiselect(fullName.alias("name"), band.alias("band"));
        return em.createQuery(cq).getResultList().stream()
                .map(t -> t.get("name", String.class) + " -> " + t.get("band", String.class))
                .toList();
    }

    // ------------------------------------------------------------------
    // 13. Dynamic search: build predicates only for the filters supplied
    // ------------------------------------------------------------------
    public List<EmployeeDto> search(EmployeeFilter f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeDto> cq = cb.createQuery(EmployeeDto.class);
        Root<Employee> e = cq.from(Employee.class);
        Join<Employee, Department> d = e.join("department", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (hasText(f.name())) {
            String like = "%" + f.name().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(e.get("firstName")), like),
                    cb.like(cb.lower(e.get("lastName")), like)));
        }
        if (hasText(f.department())) {
            predicates.add(cb.equal(d.get("name"), f.department()));
        }
        if (f.minSalary() != null) {
            predicates.add(cb.greaterThanOrEqualTo(e.<Double>get("salary"), f.minSalary()));
        }
        if (f.maxSalary() != null) {
            predicates.add(cb.lessThanOrEqualTo(e.<Double>get("salary"), f.maxSalary()));
        }
        if (f.active() != null) {
            predicates.add(cb.equal(e.get("active"), f.active()));
        }

        cq.select(cb.construct(EmployeeDto.class,
                        e.get("id"), e.get("firstName"), e.get("lastName"), e.get("salary"), d.get("name")))
          .where(predicates.toArray(new Predicate[0]))
          .orderBy(cb.asc(e.get("lastName")), cb.asc(e.get("firstName")));
        return em.createQuery(cq).getResultList();
    }

    // ------------------------------------------------------------------
    // 14. CriteriaUpdate / CriteriaDelete (bulk operations, need a transaction)
    // ------------------------------------------------------------------
    @Transactional
    public int raiseSalaryBelow(double threshold, double percent) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Employee> cu = cb.createCriteriaUpdate(Employee.class);
        Root<Employee> e = cu.from(Employee.class);
        cu.set(e.<Double>get("salary"), cb.prod(e.<Double>get("salary"), 1 + percent / 100.0))
          .where(cb.lessThan(e.<Double>get("salary"), threshold));
        return em.createQuery(cu).executeUpdate();
    }

    /** Bulk delete bypasses the persistence context and join-table cleanup: only use on rows with no links. */
    @Transactional
    public int deleteInactive() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Employee> cd = cb.createCriteriaDelete(Employee.class);
        Root<Employee> e = cd.from(Employee.class);
        cd.where(cb.isFalse(e.get("active")));
        return em.createQuery(cd).executeUpdate();
    }
}
