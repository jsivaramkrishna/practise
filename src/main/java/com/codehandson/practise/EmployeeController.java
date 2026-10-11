package com.codehandson.practise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * EmployeeController - REST API for Employee operations
 * 
 * Endpoints:
 * POST   /api/employees              - Create new employee
 * GET    /api/employees              - Get all employees
 * GET    /api/employees/high-earners - Get employees earning > $80,000
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // Allow requests from frontend
public class EmployeeController {
    
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Create a new employee
     * POST /api/employees
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee saved = employeeRepository.save(employee);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all employees
     * GET /api/employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    /**
     * Get employees earning more than $80,000
     * GET /api/employees/high-earners
     */
    @GetMapping("/high-earners")
    public ResponseEntity<List<Employee>> getHighEarners() {
        List<Employee> highEarners = employeeRepository.findEmployeesEarningMoreThan(80000.0);
        return ResponseEntity.ok(highEarners);
    }

    /**
     * Get employees hired in a specific year
     * GET /api/employees/hired-in/{year}
     */
    @GetMapping("/hired-in/{year}")
    public ResponseEntity<List<Employee>> getEmployeesHiredInYear(@PathVariable Integer year) {
        List<Employee> employees = employeeRepository.findEmployeesHiredInYear(year);
        return ResponseEntity.ok(employees);
    }

    /**
     * Get employee by ID
     * GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
