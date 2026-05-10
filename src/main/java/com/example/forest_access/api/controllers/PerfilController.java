package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.PerfilResponse;
import com.example.forest_access.biz.dao.services.PerfilService;
import com.example.forest_access.dto.PerfilDTO;
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

    // Recibe el DTO
    @PostMapping("/create")
    public ResponseEntity<PerfilResponse> create(@RequestBody PerfilDTO dto) {
        PerfilResponse creado = service.create(dto);
        URI location = URI.create("/api/perfiles/" + creado.getId()); // Ajustado sin /forest_access para mantener consistencia general, cámbialo si es necesario
        return ResponseEntity.created(location).body(creado);
    }

    // Recibe el DTO
    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> update(
            @PathVariable Long id,
            @RequestBody PerfilDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
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