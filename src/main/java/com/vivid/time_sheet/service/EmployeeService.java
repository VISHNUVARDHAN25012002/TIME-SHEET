package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerEmployee(String firstName, String lastName, String email, String rawPassword, String role) {
        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // Save the employee details and the hashed password
        String sql = "INSERT INTO Employees (fname, lname, email, password, role, status) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, firstName, lastName, email, hashedPassword, role, "ACTIVE");
    }
}
