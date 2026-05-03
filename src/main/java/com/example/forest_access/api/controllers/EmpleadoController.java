package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.services.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Empleado> create(@RequestBody Empleado empleado) {
        Empleado creado = service.create(empleado);
        URI location = URI.create("/forest_access/api/empleados/" + creado.getIdEmpleado());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> update(
            @PathVariable Integer id,
            @RequestBody Empleado datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{activo}")
    public ResponseEntity<List<Empleado>> findByActivo(@PathVariable Boolean activo) {
        return ResponseEntity.ok(service.findByActivo(activo));
    }
}