package com.codehandson.practise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * EmployeeSeeder - Fills database with initial Employee data
 * 
 * What happens:
 * 1. App starts
 * 2. This class runs automatically
 * 3. Checks if database is empty
 * 4. If empty → Adds 4 employees
 * 5. If has data → Skip (don't add duplicates)
 */
@Component  // Tells Spring: "Manage this class"
public class EmployeeSeeder implements CommandLineRunner {
    
    // This object helps us save/read employees from database
    private final EmployeeRepository repository;
    
    // For printing messages to console
    private final Logger logger = LoggerFactory.getLogger(EmployeeSeeder.class);

    // Spring automatically gives us the repository
    public EmployeeSeeder(EmployeeRepository repository) {
        this.repository = repository;
    }

    // This runs automatically when app starts
    @Override
    public void run(String... args) throws Exception {
        try {
            // Check: Is database empty?
            if (repository.count() == 0) {
                logger.info("🌱 Adding initial employees...");
                
                // Get list of employees to add
                List<Employee> employees = getInitialEmployeeData();
                
                // Add each employee to database
                for (Employee emp : employees) {
                    Employee saved = repository.save(emp);  // Hibernate saves to database
                    logger.info("✅ Saved: {} {} (ID={})", 
                        saved.getFirstName(), 
                        saved.getLastName(), 
                        saved.getEmployeeId());
                }
                
                logger.info("✅ Done! Total employees: {}", repository.count());
                
            } else {
                // Database already has employees, don't add more
                logger.info("ℹ️ Database already has {} employees", repository.count());
            }
        } catch (Exception e) {
            logger.error("❌ Error: {}", e.getMessage());
        }
    }
    
    // List of employees to add (when database is empty)
    private List<Employee> getInitialEmployeeData() {
        return Arrays.asList(
            new Employee("John", "Smith", "john.smith@company.com", LocalDate.of(2024, 1, 15)),
            new Employee("Sarah", "Johnson", "sarah.johnson@company.com", LocalDate.of(2024, 2, 20)),
            new Employee("Mike", "Williams", "mike.williams@company.com", LocalDate.of(2024, 3, 10)),
            new Employee("Emma", "Brown", "emma.brown@company.com", LocalDate.of(2024, 4, 5))
        );
    }
}

