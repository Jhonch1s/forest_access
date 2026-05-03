package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.RegistroDiario;
import com.example.forest_access.biz.dao.services.RegistroDiarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/registros-diarios")
public class RegistroDiarioController {

    private final RegistroDiarioService service;

    public RegistroDiarioController(RegistroDiarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegistroDiario>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDiario> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RegistroDiario> create(@RequestBody RegistroDiario registro) {
        RegistroDiario creado = service.create(registro);
        URI location = URI.create("/forest_access/api/registros-diarios/" + creado.getIdRegistro());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDiario> update(
            @PathVariable Integer id,
            @RequestBody RegistroDiario datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<RegistroDiario>> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.findByFecha(fecha));
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<RegistroDiario>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findPorIdEmpleado(idEmpleado));
    }
}