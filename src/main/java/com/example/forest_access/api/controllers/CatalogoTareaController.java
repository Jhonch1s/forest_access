package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.services.CatalogoTareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/catalogo-tareas")
public class CatalogoTareaController {

    private final CatalogoTareaService service;

    public CatalogoTareaController(CatalogoTareaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CatalogoTarea>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoTarea> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CatalogoTarea> create(@RequestBody CatalogoTarea tarea) {
        CatalogoTarea creada = service.create(tarea);
        URI location = URI.create("/api/catalogo-tareas/" + creada.getIdCatalogoTarea());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogoTarea> update(
            @PathVariable Integer id,
            @RequestBody CatalogoTarea datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sin-habilitacion")
    public ResponseEntity<List<CatalogoTarea>> findSinHabilitacion() {
        return ResponseEntity.ok(service.findSinHabilitacion());
    }

    @PostMapping("/por-habilitacion")
    public ResponseEntity<List<CatalogoTarea>> findPorHabilitacion(@RequestBody Habilitacion h) {
        return ResponseEntity.ok(service.findPorHabilitacion(h));
    }
}