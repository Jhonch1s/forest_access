package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.services.HistoricoTratamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/historico_tratamientos")
public class HistoricoTratamientoController {

    private HistoricoTratamientoService service;

    public HistoricoTratamientoController(HistoricoTratamientoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HistoricoTratamiento>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<HistoricoTratamiento> create(@RequestBody HistoricoTratamiento historico) {
        HistoricoTratamiento nuevo = service.create(historico);
        URI location = URI.create("/forest_access/api/historico_tratamientos/" + nuevo.getIdHistorico());
        return ResponseEntity.created(location).body(nuevo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
