package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.CatalogoTareaRequest;
import com.example.forest_access.api.controllers.response.CatalogoTareaResponse;
import com.example.forest_access.biz.dao.services.CatalogoTareaService;
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
    public ResponseEntity<CatalogoTareaResponse> create(@RequestBody CatalogoTareaRequest request) {
        CatalogoTareaResponse creada = service.create(request);
        return ResponseEntity.created(URI.create("/api/catalogo-tareas/" + creada.getIdCatalogoTarea())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogoTareaResponse> update(
            @PathVariable Integer id,
            @RequestBody CatalogoTareaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}