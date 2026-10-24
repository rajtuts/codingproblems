package com.example.criteriademo;

import com.example.criteriademo.entity.Department;
import com.example.criteriademo.entity.Employee;
import com.example.criteriademo.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Order(1)
public class DataLoader implements CommandLineRunner {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void run(String... args) {
        Department eng = new Department("Engineering", "Hyderabad");
        Department hr = new Department("HR", "Mumbai");
        Department sales = new Department("Sales", "Delhi");
        Project apollo = new Project("Apollo", 500_000);
        Project gemini = new Project("Gemini", 150_000);
        Project orion = new Project("Orion", 80_000);
        for (Object o : new Object[]{eng, hr, sales, apollo, gemini, orion}) em.persist(o);

        emp("Alice", "Smith", "alice@ex.com", "9990001", 29, 120000, true, "2021-03-15", eng, apollo, gemini);
        emp("Bob", "Jones", "bob@ex.com", null, 35, 95000, true, "2018-07-01", eng, apollo);
        emp("Carol", "White", "carol@ex.com", "9990003", 41, 70000, true, "2015-01-20", hr, orion);
        emp("David", "Brown", "david@ex.com", null, 26, 60000, true, "2023-05-10", sales, gemini);
        emp("Eve", "Smith", "eve@ex.com", "9990005", 38, 135000, true, "2016-11-30", eng, apollo, orion);
        emp("Frank", "Miller", "frank@ex.com", "9990006", 52, 55000, false, "2010-09-09", hr);
        emp("Grace", "Lee", "grace@ex.com", "9990007", 31, 88000, true, "2020-02-02", sales, gemini, orion);
        emp("Aaron", "Clark", "aaron@ex.com", null, 23, 45000, true, "2024-06-01", sales);
    }

    private void emp(String fn, String ln, String email, String phone, int age, double salary,
                     boolean active, String hired, Department d, Project... projects) {
        Employee e = new Employee(fn, ln, email, phone, age, salary, active, LocalDate.parse(hired), d);
        for (Project p : projects) e.getProjects().add(p);
        em.persist(e);
    }
}
