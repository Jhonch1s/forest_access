package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Perfil;
import com.example.forest_access.biz.dao.services.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/perfiles")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Perfil> create(@RequestBody Perfil perfil) {
        Perfil creado = service.create(perfil);
        URI location = URI.create("/forest_access/api/perfiles/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> update(@PathVariable Long id, @RequestBody Perfil datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint adicional para buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Perfil> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.findByNombre(nombre));
    }
}