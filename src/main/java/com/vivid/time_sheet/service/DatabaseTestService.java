package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testConnection() {
        String sql = "SELECT COUNT(*) FROM Roles";  // Assuming you have a Roles table
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("Number of roles in the Roles table: " + count);
    }
}
