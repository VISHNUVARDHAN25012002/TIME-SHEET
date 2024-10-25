// TimesheetController.java
package com.vivid.time_sheet.controller;

import com.vivid.time_sheet.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    // Soft delete (deactivate) endpoint for a timesheet entry
    @PutMapping("/{timesheetId}/deactivate")
    public void deactivateTimesheetEntry(@PathVariable int timesheetId, @RequestParam boolean isActive) {
        timesheetService.deactivateTimesheetEntry(timesheetId, isActive);
    }

    // Get all active timesheet entries
    @GetMapping("/active")
    public List<Map<String, Object>> getActiveTimesheets() {
        return timesheetService.getActiveTimesheets();
    }

    // Add a new timesheet entry
    @PostMapping("/add")
    public void addTimesheetEntry(@RequestParam int employeeId, @RequestParam int projectId,
                                  @RequestParam double workHours, @RequestParam String description) {
        timesheetService.addTimesheetEntry(employeeId, projectId, workHours, description);
    }

    // Update an existing timesheet entry
    @PutMapping("/{timesheetId}/update")
    public void updateTimesheetEntry(@PathVariable int timesheetId, @RequestParam double workHours,
                                     @RequestParam String description) {
        timesheetService.updateTimesheetEntry(timesheetId, workHours, description);
    }
}
