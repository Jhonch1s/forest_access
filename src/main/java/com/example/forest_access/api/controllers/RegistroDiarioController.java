package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.RegistroDiarioResponse;
import com.example.forest_access.biz.dao.services.RegistroDiarioService;
import com.example.forest_access.dto.RegistroDiarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/registros-diarios")
@AllArgsConstructor
public class RegistroDiarioController {

    private final RegistroDiarioService service;

    @GetMapping
    public ResponseEntity<List<RegistroDiarioResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDiarioResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RegistroDiarioResponse> create(@RequestBody RegistroDiarioDTO dto) {
        RegistroDiarioResponse creada = service.create(dto);
        return ResponseEntity.created(URI.create("/api/registros-diarios/" + creada.getIdRegistro())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDiarioResponse> update(
            @PathVariable Integer id,
            @RequestBody RegistroDiarioDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<RegistroDiarioResponse>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findPorIdEmpleado(idEmpleado));
    }
}