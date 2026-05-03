package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.services.EmpleadoCuadrillaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/empleados-cuadrillas")
public class EmpleadoCuadrillaController {

    private final EmpleadoCuadrillaService service;

    public EmpleadoCuadrillaController(EmpleadoCuadrillaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoCuadrilla>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<EmpleadoCuadrilla> findById(
            @RequestParam Integer idCuadrilla,
            @RequestParam Integer idEmpleado,
            @RequestParam String fechaInicio) {

        EmpleadoCuadrillaId id = new EmpleadoCuadrillaId(idCuadrilla, idEmpleado, LocalDate.parse(fechaInicio));
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmpleadoCuadrilla> create(@RequestBody EmpleadoCuadrilla relacion) {
        EmpleadoCuadrilla creada = service.create(relacion);
        return ResponseEntity.created(URI.create("/forest_access/api/empleados-cuadrillas")).body(creada);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestParam Integer idCuadrilla,
            @RequestParam Integer idEmpleado,
            @RequestParam String fechaInicio) {

        EmpleadoCuadrillaId id = new EmpleadoCuadrillaId(idCuadrilla, idEmpleado, LocalDate.parse(fechaInicio));
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cuadrilla/{idCuadrilla}")
    public ResponseEntity<List<EmpleadoCuadrilla>> findByCuadrilla(@PathVariable Integer idCuadrilla) {
        return ResponseEntity.ok(service.findByCuadrilla(idCuadrilla));
    }
}