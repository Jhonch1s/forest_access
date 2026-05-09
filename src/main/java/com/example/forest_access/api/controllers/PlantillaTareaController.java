package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.PlantillaTareaRequest;
import com.example.forest_access.api.controllers.response.PlantillaTareaResponse;
import com.example.forest_access.biz.dao.services.PlantillaTareaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/plantillas-tarea")
@AllArgsConstructor
public class PlantillaTareaController {

    private final PlantillaTareaService service;

    @GetMapping
    public ResponseEntity<List<PlantillaTareaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaTareaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PlantillaTareaResponse> create(@RequestBody PlantillaTareaRequest request) {
        PlantillaTareaResponse creada = service.create(request);
        URI location = URI.create("/forest_access/api/plantillas-tarea/" + creada.getIdPlantilla());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaTareaResponse> update(
            @PathVariable Integer id,
            @RequestBody PlantillaTareaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/catalogo/{idCatalogo}")
    public ResponseEntity<List<PlantillaTareaResponse>> findByCatalogo(@PathVariable Integer idCatalogo) {
        return ResponseEntity.ok(service.findByCatalogo(idCatalogo));
    }
}