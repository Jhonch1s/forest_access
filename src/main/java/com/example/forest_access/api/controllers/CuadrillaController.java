package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.services.CuadrillaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/cuadrillas")
public class CuadrillaController {

    private final CuadrillaService service;

    public CuadrillaController(CuadrillaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cuadrilla>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuadrilla> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Cuadrilla>> findActivas() {
        return ResponseEntity.ok(service.findActivas());
    }

    @PostMapping("/create")
    public ResponseEntity<Cuadrilla> create(@RequestBody Cuadrilla cuadrilla) {
        Cuadrilla creada = service.create(cuadrilla);
        URI location = URI.create("/forest_access/api/cuadrillas/" + creada.getIdCuadrilla());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuadrilla> update(
            @PathVariable Integer id,
            @RequestBody Cuadrilla datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}