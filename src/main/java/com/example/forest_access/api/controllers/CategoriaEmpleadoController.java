package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.services.CategoriaEmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/categorias-empleado")
public class CategoriaEmpleadoController {

    private final CategoriaEmpleadoService service;

    public CategoriaEmpleadoController(CategoriaEmpleadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaEmpleado>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEmpleado> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CategoriaEmpleado> create(@RequestBody CategoriaEmpleado categoria) {
        CategoriaEmpleado creada = service.create(categoria);
        URI location = URI.create("/api/categorias-empleado/" + creada.getIdCategoria());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaEmpleado> update(
            @PathVariable Integer id,
            @RequestBody CategoriaEmpleado datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
