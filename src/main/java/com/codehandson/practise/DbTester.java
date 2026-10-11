package com.codehandson.practise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DbTester - Tests database connection and demonstrates:
 * 1. Raw SQL queries (old way - NOT recommended for production)
 * 2. Hibernate/JPA queries (best practice)
 * 
 * This shows the difference between:
 * - JDBC: You write SQL manually
 * - JPA/Hibernate: You use Java objects, Hibernate writes SQL
 */
@Component
public class DbTester implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DbTester.class);
    
    // OLD WAY: Raw JDBC for SQL queries
    private final JdbcTemplate jdbcTemplate;
    
    // NEW WAY: JPA Repository (much better!)
    private final EmployeeRepository employeeRepository;

    // Spring injects both
    public DbTester(JdbcTemplate jdbcTemplate, EmployeeRepository employeeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Test 1: Simple connection test
            testDatabaseConnection();
            
            // Test 2: OLD WAY - Raw SQL (don't use in production!)
            testRawSQL();
            
            // Test 3: NEW WAY - Hibernate/JPA (best practice!)
            testHibernateQuery();
            
        } catch (Exception e) {
            logger.error("Database test failed", e);
        }
    }
    
    /**
     * Test 1: Basic database connection
     * Just checks: "Is database accessible?"
     */
    private void testDatabaseConnection() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        logger.info("✅ Database connection test successful, SELECT 1 returned: {}", result);
    }
    
    /**
     * Test 2: OLD WAY - Using raw SQL (JDBC)
     * 
     * ❌ Why avoid this in production:
     * - Hard to maintain SQL strings in Java
     * - Easy to have SQL injection vulnerabilities
     * - No type safety
     * - Difficult to refactor
     */
    private void testRawSQL() {
        logger.info("\n📊 OLD WAY - Raw SQL Query (JDBC):");
        logger.info("SQL: SELECT employee_id, first_name, last_name, email, status FROM employee ORDER BY employee_id");
        
        String sql = "SELECT employee_id, first_name, last_name, email, status FROM employee ORDER BY employee_id";
        
        try {
            // This returns a List of Maps (not type-safe)
            List<java.util.Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            
            logger.info("Total employees: {}", results.size());
            for (java.util.Map<String, Object> row : results) {
                logger.info("  ID: {} | Name: {} {} | Email: {} | Status: {}",
                    row.get("employee_id"),
                    row.get("first_name"),
                    row.get("last_name"),
                    row.get("email"),
                    row.get("status")
                );
            }
        } catch (Exception e) {
            logger.warn("Could not fetch data with raw SQL: {}", e.getMessage());
        }
    }
    
    /**
     * Test 3: NEW WAY - Using Hibernate/JPA
     * 
     * ✅ Why use this:
     * - Type-safe (you get Employee objects, not Maps)
     * - Easy to read and maintain
     * - SQL is generated automatically
     * - Works with any database (PostgreSQL, MySQL, Oracle, etc.)
     * - Better for refactoring
     * 
     * This is the RECOMMENDED approach!
     */
    private void testHibernateQuery() {
        logger.info("\n🎯 NEW WAY - Hibernate/JPA Query:");
        logger.info("Code: employeeRepository.findAll()");
        logger.info("(Hibernate converts this to the same SQL query automatically)\n");
        
        // Method 1: Find ALL employees using Hibernate
        List<Employee> allEmployees = employeeRepository.findAll();
        
        logger.info("✅ Total employees: {}", allEmployees.size());
        
        // Loop through employees - they're actual Java objects!
        for (Employee emp : allEmployees) {
            logger.info("  ID: {} | Name: {} {} | Email: {} | Status: {} | Hired: {}",
                emp.getEmployeeId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmail(),
                emp.getStatus(),
                emp.getHireDate()
            );
        }
        
        // Method 2: Count total employees
        long totalCount = employeeRepository.count();
        logger.info("\n📈 Total employee count (using Hibernate): {}", totalCount);
        
        // Method 3: Access employee details (bonus example)
        if (!allEmployees.isEmpty()) {
            Employee firstEmployee = allEmployees.get(0);
            logger.info("\n👤 First employee details:");
            logger.info("   Name: {} {}", firstEmployee.getFirstName(), firstEmployee.getLastName());
            logger.info("   Email: {}", firstEmployee.getEmail());
            logger.info("   Hire Date: {}", firstEmployee.getHireDate());
            logger.info("   Status: {}", firstEmployee.getStatus());
            logger.info("   Created At: {}", firstEmployee.getCreatedAt());
        }
    }
}

