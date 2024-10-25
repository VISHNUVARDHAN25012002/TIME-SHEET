// TimesheetService.java
package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class TimesheetService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a new timesheet entry with project_id
    public void addTimesheetEntry(int employeeId, int projectId, double workHours, String description) {
        String sql = "INSERT INTO Timesheet (employee_id, project_id, workhour, date, task_description, is_active) VALUES (?, ?, ?, NOW(), ?, 1)";
        jdbcTemplate.update(sql, employeeId, projectId, workHours, description);
    }

    // Get all active timesheet entries
    public List<Map<String, Object>> getActiveTimesheets() {
        String sql = "SELECT * FROM Timesheet WHERE is_active = 1";
        return jdbcTemplate.queryForList(sql);
    }

    // Update a timesheet entry
    public void updateTimesheetEntry(int timesheetId, double workHours, String description) {
        String sql = "UPDATE Timesheet SET workhour = ?, task_description = ? WHERE id = ?";
        jdbcTemplate.update(sql, workHours, description, timesheetId);
    }

    // Soft delete (deactivate) a timesheet entry by setting is_active to 0
    public void deactivateTimesheetEntry(int timesheetId, boolean isActive) {
        String sql = "UPDATE Timesheet SET is_active = ? WHERE id = ?";
        jdbcTemplate.update(sql, isActive ? 1 : 0, timesheetId);
    }
}
