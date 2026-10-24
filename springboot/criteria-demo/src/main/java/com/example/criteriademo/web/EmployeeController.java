package com.example.criteriademo.web;

import com.example.criteriademo.dao.EmployeeCriteriaDao;
import com.example.criteriademo.dto.DeptStats;
import com.example.criteriademo.dto.EmployeeDto;
import com.example.criteriademo.dto.EmployeeFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeCriteriaDao dao;

    public EmployeeController(EmployeeCriteriaDao dao) {
        this.dao = dao;
    }

    // GET /api/employees/search?name=a&department=Sales&minSalary=50000&maxSalary=100000&active=true
    @GetMapping("/search")
    public List<EmployeeDto> search(EmployeeFilter filter) {
        return dao.search(filter);
    }

    // GET /api/employees/dept-stats?minHeadcount=1
    @GetMapping("/dept-stats")
    public List<DeptStats> deptStats(@org.springframework.web.bind.annotation.RequestParam(defaultValue = "1") long minHeadcount) {
        return dao.statsByDepartment(minHeadcount);
    }

    // GET /api/employees/salary-stats
    @GetMapping("/salary-stats")
    public Map<String, Object> salaryStats() {
        return dao.salaryStats();
    }
}
