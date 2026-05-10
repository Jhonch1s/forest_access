package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.PlantillaTareaResponse;
import com.example.forest_access.biz.dao.services.PlantillaTareaService;
import com.example.forest_access.dto.PlantillaTareaDTO;
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
    public ResponseEntity<PlantillaTareaResponse> create(@RequestBody PlantillaTareaDTO dto) {
        PlantillaTareaResponse creada = service.create(dto);
        URI location = URI.create("/api/plantillas-tarea/" + creada.getIdPlantilla());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaTareaResponse> update(
            @PathVariable Integer id,
            @RequestBody PlantillaTareaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
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