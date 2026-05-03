package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.PlantillaTarea;
import com.example.forest_access.biz.dao.services.PlantillaTareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/plantillas-tarea")
public class PlantillaTareaController {

    private final PlantillaTareaService service;

    public PlantillaTareaController(PlantillaTareaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PlantillaTarea>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaTarea> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PlantillaTarea> create(@RequestBody PlantillaTarea plantilla) {
        PlantillaTarea creada = service.create(plantilla);
        URI location = URI.create("/forest_access/api/plantillas-tarea/" + creada.getIdPlantilla());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaTarea> update(
            @PathVariable Integer id,
            @RequestBody PlantillaTarea datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/catalogo/{idCatalogo}")
    public ResponseEntity<List<PlantillaTarea>> findByCatalogo(@PathVariable Integer idCatalogo) {
        return ResponseEntity.ok(service.findByCatalogo(idCatalogo));
    }
}