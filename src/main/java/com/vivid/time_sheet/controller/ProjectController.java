// ProjectController.java
package com.vivid.time_sheet.controller;

import com.vivid.time_sheet.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Soft delete (deactivate) endpoint for a project
    @PutMapping("/{projectId}/deactivate")
    public void deactivateProject(@PathVariable int projectId, @RequestParam boolean isActive) {
        projectService.deactivateProject(projectId, isActive);
    }

    // Get all active projects
    @GetMapping("/active")
    public List<Map<String, Object>> getAllActiveProjects() {
        return projectService.getAllActiveProjects();
    }

    // Add a new project
    @PostMapping("/add")
    public void addProject(@RequestBody Map<String, Object> projectData) {
        String projectCode = (String) projectData.get("projectCode");
        String projectName = (String) projectData.get("projectName");
        String status = (String) projectData.get("status");
        int creatorId = (int) projectData.get("creatorId");

        projectService.addProject(projectCode, projectName, status, creatorId);
    }

    // Update an existing project
    @PutMapping("/{projectId}/update")
    public void updateProject(@PathVariable int projectId, @RequestParam String projectName,
                              @RequestParam String status, @RequestParam int modifiedBy) {
        projectService.updateProject(projectId, projectName, status, modifiedBy);
    }
}
