package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.CatalogoTareaResponse;
import com.example.forest_access.biz.dao.services.CatalogoTareaService;
import com.example.forest_access.dto.CatalogoTareaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catalogo-tareas")
@AllArgsConstructor
public class CatalogoTareaController {

    private final CatalogoTareaService service;

    @GetMapping
    public ResponseEntity<List<CatalogoTareaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoTareaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CatalogoTareaResponse> create(@RequestBody CatalogoTareaDTO dto) {
        CatalogoTareaResponse creada = service.create(dto);
        return ResponseEntity.created(URI.create("/api/catalogo-tareas/" + creada.getIdCatalogoTarea())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogoTareaResponse> update(
            @PathVariable Integer id,
            @RequestBody CatalogoTareaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}