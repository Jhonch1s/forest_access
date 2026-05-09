package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.EmpleadoRequest;
import com.example.forest_access.api.controllers.response.EmpleadoResponse;
import com.example.forest_access.biz.dao.services.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@AllArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmpleadoResponse> create(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse creado = service.create(request);
        URI location = URI.create("/forest_access/api/empleados/" + creado.getIdEmpleado());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> update(
            @PathVariable Integer id,
            @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}