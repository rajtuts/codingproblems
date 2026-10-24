package com.example.projections.controller;

import com.example.projections.dto.EmployeeDto;
import com.example.projections.projection.*;
import com.example.projections.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) { this.repo = repo; }

    // GET /api/employees/summary
    @GetMapping("/summary")
    public List<EmployeeSummary> summary() { return repo.findByActiveTrue(); }

    // GET /api/employees/stats
    @GetMapping("/stats")
    public List<DepartmentStats> stats() { return repo.getDepartmentStats(); }

    // GET /api/employees/dept/Engineering?view=dto   (or view=summary)
    @GetMapping("/dept/{name}")
    public List<?> byDept(@PathVariable String name,
                          @RequestParam(defaultValue = "summary") String view) {
        return "dto".equals(view)
                ? repo.findByDepartmentName(name, EmployeeDto.class)
                : repo.findByDepartmentName(name, EmployeeSummary.class);
    }
}
