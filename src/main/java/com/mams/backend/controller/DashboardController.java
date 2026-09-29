package com.mams.backend.controller;

import com.mams.backend.dto.DashboardMetricsResponse;
import com.mams.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER')")
    public ResponseEntity<DashboardMetricsResponse> getMetrics(
            @RequestParam(required = false) Long baseId,
            @RequestParam(required = false) Long equipmentTypeId) {
        
        return ResponseEntity.ok(dashboardService.getMetrics(baseId, equipmentTypeId));
    }
}
