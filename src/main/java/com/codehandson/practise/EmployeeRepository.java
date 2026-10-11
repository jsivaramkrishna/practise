package com.codehandson.practise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * EmployeeRepository - Data access layer for Employee entity
 * 
 * Spring Data JPA automatically implements these methods.
 * Hibernate generates SQL based on method names and @Query annotations.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Query B: Find employees earning more than a specified amount
     * 
     * SQL Equivalent:
     * SELECT * FROM employee WHERE salary > ?
     * ORDER BY salary DESC
     */
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary ORDER BY e.salary DESC")
    List<Employee> findEmployeesEarningMoreThan(@Param("minSalary") Double minSalary);
}

