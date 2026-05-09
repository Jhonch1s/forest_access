package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.PerfilRequest;
import com.example.forest_access.api.controllers.response.PerfilResponse;
import com.example.forest_access.biz.dao.services.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
@AllArgsConstructor
public class PerfilController {

    private final PerfilService service;

    @GetMapping
    public ResponseEntity<List<PerfilResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PerfilResponse> create(@RequestBody PerfilRequest request) {
        PerfilResponse creado = service.create(request);
        URI location = URI.create("/forest_access/api/perfiles/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> update(
            @PathVariable Long id,
            @RequestBody PerfilRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PerfilResponse> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.findByNombre(nombre));
    }
}