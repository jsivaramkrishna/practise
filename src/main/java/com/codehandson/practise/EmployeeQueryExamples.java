package com.codehandson.practise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * EmployeeQueryExamples - Learning Hibernate/JPA Query Methods
 * 
 * This class shows you how to use Hibernate to query employees.
 * 
 * ✅ WHAT IS HIBERNTE/JPA:
 * - Hibernate converts Java objects to SQL automatically
 * - You don't write SQL manually
 * - You work with Java objects (Employee) instead of SQL
 * 
 * SQL Equivalent:
 * SELECT employee_id, first_name, last_name, email, status, hire_date
 * FROM employee
 * ORDER BY employee_id;
 */
@Component
public class EmployeeQueryExamples implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeQueryExamples.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeQueryExamples(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("\n" + "=".repeat(70));
            logger.info("🎓 HIBERNATE/JPA QUERY EXAMPLES");
            logger.info("=".repeat(70));
            
            // Example 1: Get all employees
            example1_FindAllEmployees();
            
            // Example 2: Count total employees
            example2_CountEmployees();
            
            // Example 3: Get first employee
            example3_GetFirstEmployee();
            
            // Example 4: Display all employee details
            example4_DisplayAllEmployees();
            
            logger.info("=".repeat(70) + "\n");
            
        } catch (Exception e) {
            logger.error("❌ Error: {}", e.getMessage());
        }
    }
    
    /**
     * EXAMPLE 1: Get ALL employees
     * 
     * Hibernate/JPA way:
     * List<Employee> employees = employeeRepository.findAll();
     * 
     * Equivalent SQL:
     * SELECT * FROM employee;
     */
    private void example1_FindAllEmployees() {
        logger.info("\n📌 Example 1: Find ALL Employees");
        logger.info("Code: employeeRepository.findAll()");
        logger.info("SQL:  SELECT * FROM employee;\n");
        
        List<Employee> employees = employeeRepository.findAll();
        
        logger.info("Result: Found {} employees", employees.size());
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            logger.info("  [{}] {} {} - {}", 
                (i + 1), 
                emp.getFirstName(), 
                emp.getLastName(), 
                emp.getEmail()
            );
        }
    }
    
    /**
     * EXAMPLE 2: Count total employees
     * 
     * Hibernate/JPA way:
     * long count = employeeRepository.count();
     * 
     * Equivalent SQL:
     * SELECT COUNT(*) FROM employee;
     */
    private void example2_CountEmployees() {
        logger.info("\n📌 Example 2: Count ALL Employees");
        logger.info("Code: employeeRepository.count()");
        logger.info("SQL:  SELECT COUNT(*) FROM employee;\n");
        
        long count = employeeRepository.count();
        logger.info("Result: Total employees = {}", count);
    }
    
    /**
     * EXAMPLE 3: Get first employee with details
     * 
     * Hibernate/JPA way:
     * List<Employee> employees = employeeRepository.findAll();
     * Employee first = employees.get(0);
     * 
     * Equivalent SQL:
     * SELECT * FROM employee LIMIT 1;
     */
    private void example3_GetFirstEmployee() {
        logger.info("\n📌 Example 3: Get FIRST Employee");
        logger.info("Code: employeeRepository.findAll().get(0)");
        logger.info("SQL:  SELECT * FROM employee LIMIT 1;\n");
        
        List<Employee> employees = employeeRepository.findAll();
        
        if (!employees.isEmpty()) {
            Employee first = employees.get(0);
            logger.info("Result: First employee = {}", first.getFirstName());
            logger.info("  Full Name: {} {}", first.getFirstName(), first.getLastName());
            logger.info("  Email: {}", first.getEmail());
            logger.info("  Hire Date: {}", first.getHireDate());
            logger.info("  Status: {}", first.getStatus());
        } else {
            logger.info("No employees found!");
        }
    }
    
    /**
     * EXAMPLE 4: Display ALL employees with all details
     * 
     * This shows the EXACT comparison between SQL and Hibernate:
     */
    private void example4_DisplayAllEmployees() {
        logger.info("\n📌 Example 4: Display ALL Employees (Full Details)");
        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║ THIS SQL QUERY:                                                ║");
        logger.info("║ SELECT employee_id, first_name, last_name, email, status,     ║");
        logger.info("║        hire_date, created_at                                  ║");
        logger.info("║ FROM employee                                                 ║");
        logger.info("║ ORDER BY employee_id;                                         ║");
        logger.info("╠════════════════════════════════════════════════════════════════╣");
        logger.info("║ BECOMES THIS HIBERNATE CODE:                                   ║");
        logger.info("║ List<Employee> emps = employeeRepository.findAll();           ║");
        logger.info("║ for (Employee e : emps) { e.getFirstName(), ... }             ║");
        logger.info("╚════════════════════════════════════════════════════════════════╝\n");
        
        List<Employee> employees = employeeRepository.findAll();
        
        logger.info("Results ({} employees):\n", employees.size());
        logger.info("ID  | First Name | Last Name  | Email                      | Status | Hire Date  | Created At");
        logger.info("----+------------+------------+----------------------------+--------+------------+---------------------");
        
        for (Employee emp : employees) {
            logger.info("{} | {} | {} | {} | {} | {} | {}",
                String.format("%2d", emp.getEmployeeId()),
                String.format("%-10s", emp.getFirstName()),
                String.format("%-10s", emp.getLastName()),
                String.format("%-26s", emp.getEmail()),
                String.format("%-6s", emp.getStatus()),
                emp.getHireDate(),
                emp.getCreatedAt()
            );
        }
    }
}
