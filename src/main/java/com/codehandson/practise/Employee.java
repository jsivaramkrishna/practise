// ...existing code...
package com.codehandson.practise;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId; // Maps to SERIAL PRIMARY KEY

    @Column(nullable = false)
    private String firstName; // Maps to NOT NULL

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email; // Maps to UNIQUE NOT NULL

    @Column(length = 20)
    @ColumnDefault("'Active'")
    private String status; // Maps to DEFAULT 'Active'

    @Column
    private LocalDate hireDate;

    @Column(columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // Maps to CURRENT_TIMESTAMP

    // constructors
    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
    }

    // getters and setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

