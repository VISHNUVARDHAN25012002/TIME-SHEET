package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    // Register a new user
    public boolean registerUser(String email, String password, String fname, String lname, String role, String status) {
        String sql = "INSERT INTO Employees (email, jdate, password, fname, lname, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password); // Hash the password before storing

            // Use the current date for 'jdate'
            Date joiningDate = Date.valueOf(LocalDate.now());

            int result = jdbcTemplate.update(sql, email, joiningDate, hashedPassword, fname, lname, role, status);

            return result > 0;
        } catch (Exception e) {
            logger.error("Error registering user with email: {}", email, e);
            return false;
        }
    }

    // Validate user credentials
    public boolean validateUser(String email, String password) {
        String sql = "SELECT password FROM Employees WHERE email = ?";

        try {
            String storedPassword = jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, storedPassword); // Validate the password
        } catch (EmptyResultDataAccessException e) {
            // Log the error and return false, meaning user not found
            logger.error("No user found with email: {}", email);
            return false;
        } catch (Exception e) {
            logger.error("Error validating user with email: {}", email, e);
            return false;
        }
    }


    public List<GrantedAuthority> getAuthorities(String email) {
        String query = "SELECT r.role_name FROM roles r " +
                "JOIN user_roles ur ON r.id = ur.role_id " +
                "JOIN users u ON ur.user_id = u.id WHERE u.email = ?";

        List<String> roles = jdbcTemplate.queryForList(query, new Object[]{email}, String.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        }

        return authorities;
    }
}
