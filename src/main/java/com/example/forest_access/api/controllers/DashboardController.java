package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.services.DashboardService;
import com.example.forest_access.dto.dashboard.DashboardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints para el panel de resumen principal")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Obtener datos del dashboard", description = "Devuelve estadísticas, cuadrillas activas y habilitaciones por vencer consolidadas.")
    public ResponseEntity<DashboardDTO> getDashboardData() {
        return ResponseEntity.ok(dashboardService.obtenerDatosDashboard());
    }
}
