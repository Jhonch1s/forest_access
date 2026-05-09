package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.services.HistoricoTratamientoService;
import com.example.forest_access.dto.HistoricoTratamientoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/historico_tratamientos")
public class HistoricoTratamientoController {

    private HistoricoTratamientoService service;

    public HistoricoTratamientoController(HistoricoTratamientoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HistoricoTratamientoDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<HistoricoTratamientoDTO> create(@RequestBody HistoricoTratamientoDTO historico) {
        HistoricoTratamiento nuevo = service.create(historico);
        URI location = URI.create("/forest_access/api/historico_tratamientos/" + nuevo.getIdHistorico());
        return ResponseEntity.created(location).body(historico);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HistoricoTratamientoDTO> delete(@PathVariable Integer id) {
        HistoricoTratamientoDTO htdto = service.delete(id);
        return ResponseEntity.ok(htdto);
    }

}
