package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.EmpleadoCuadrillaResponse;
import com.example.forest_access.biz.dao.services.EmpleadoCuadrillaService;
import com.example.forest_access.dto.EmpleadoCuadrillaDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/empleados-cuadrillas")
@AllArgsConstructor
public class EmpleadoCuadrillaController {

    private final EmpleadoCuadrillaService service;

    @GetMapping
    public ResponseEntity<List<EmpleadoCuadrillaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<EmpleadoCuadrillaResponse> create(@RequestBody EmpleadoCuadrillaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestParam Integer idCuadrilla,
            @RequestParam Integer idEmpleado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {
        service.delete(idCuadrilla, idEmpleado, fechaInicio);
        return ResponseEntity.noContent().build();
    }
}