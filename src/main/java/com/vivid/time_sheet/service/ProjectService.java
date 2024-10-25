// ProjectService.java
package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a new project
    public void addProject(String projectCode, String projectName, String status, int creatorId) {
        String sql = "INSERT INTO Projects (projectcode, projectname, status, creatorid, startdate, is_active) VALUES (?, ?, ?, ?, NOW(), 1)";
        jdbcTemplate.update(sql, projectCode, projectName, status, creatorId);
    }

    // Get all active projects
    public List<Map<String, Object>> getAllActiveProjects() {
        String sql = "SELECT * FROM Projects WHERE is_active = 1";
        return jdbcTemplate.queryForList(sql);
    }

    // Update a project
    public void updateProject(int projectId, String projectName, String status, int modifiedBy) {
        String sql = "UPDATE Projects SET projectname = ?, status = ?, lastmodifieddate = NOW(), modifiedby = ? WHERE projectid = ?";
        jdbcTemplate.update(sql, projectName, status, modifiedBy, projectId);
    }

    // Soft delete (deactivate) a project by setting is_active to 0 or 1
    public void deactivateProject(int projectId, boolean isActive) {
        String sql = "UPDATE Projects SET is_active = ? WHERE projectid = ?";
        jdbcTemplate.update(sql, isActive ? 1 : 0, projectId);
    }
}
