//package com.vivid.time_sheet.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class LoginService {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
//    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public boolean validateUser(String email, String password) {
//        String sql = "SELECT password FROM Employees WHERE email = ?";
//        try {
//            String storedPassword = jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
//            boolean isMatch = BCrypt.checkpw(password, storedPassword);
//            logger.info("Password match status: " + isMatch);
//            return isMatch;
//        } catch (Exception e) {
//            logger.error("Error validating user with email: {}", email, e);
//            return false;
//        }
//    }
//
//    // Register a new user
//    public boolean registerUser(String email, String password, String fname, String lname, String role, String status) {
//        String sql = "INSERT INTO Employees (email, password, fname, lname, role, status) VALUES (?, ?, ?, ?, ?, ?)";
//        try {
//            // Hash the password using BCrypt
//            String hashedPassword = passwordEncoder.encode(password);
//            jdbcTemplate.update(sql, email, hashedPassword, fname, lname, role, status);
//            logger.info("User registered successfully with email: " + email);
//            return true;
//        } catch (Exception e) {
//            logger.error("Error registering user with email: {}", email, e);
//            return false;
//        }
//    }
//}
