package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Liquidacion;
import com.example.forest_access.biz.dao.services.LiquidacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/liquidaciones")
public class LiquidacionController {

    private final LiquidacionService service;

    public LiquidacionController(LiquidacionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Liquidacion>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Liquidacion> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Liquidacion> create(@RequestBody Liquidacion liquidacion) {
        Liquidacion creada = service.create(liquidacion);
        URI location = URI.create("/forest_access/api/liquidaciones/" + creada.getIdLiquidacion());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Liquidacion> update(@PathVariable Integer id, @RequestBody Liquidacion datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<Liquidacion>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findByEmpleado(idEmpleado));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Liquidacion>> findByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(service.findByPeriodo(desde, hasta));
    }
}