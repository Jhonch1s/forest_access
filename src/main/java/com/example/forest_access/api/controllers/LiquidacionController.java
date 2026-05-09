package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.LiquidacionRequest;
import com.example.forest_access.api.controllers.response.LiquidacionResponse;
import com.example.forest_access.biz.dao.services.LiquidacionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/liquidaciones")
@AllArgsConstructor
public class LiquidacionController {

    private final LiquidacionService service;

    @GetMapping
    public ResponseEntity<List<LiquidacionResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiquidacionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<LiquidacionResponse> create(@RequestBody LiquidacionRequest request) {
        LiquidacionResponse creada = service.create(request);
        return ResponseEntity.created(URI.create("/api/liquidaciones/" + creada.getIdLiquidacion())).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<LiquidacionResponse>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findByEmpleado(idEmpleado));
    }
}