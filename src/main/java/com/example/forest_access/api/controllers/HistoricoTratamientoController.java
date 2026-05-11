package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.HistoricoTratamientoResponse;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.services.HistoricoTratamientoService;
import com.example.forest_access.dto.HistoricoTratamientoDTO;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<List<HistoricoTratamientoResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<HistoricoTratamientoResponse> create(@RequestBody HistoricoTratamientoDTO historico) {
        HistoricoTratamiento nuevo = service.create(historico);
        HistoricoTratamientoResponse htr = new HistoricoTratamientoResponse();
        BeanUtils.copyProperties(nuevo,htr);
        htr.setNombreParcela(nuevo.getParcela().getNombre());
        htr.setNombreTratamiento(nuevo.getTratamiento().getNombre());
        htr.setNombreCuadrilla(nuevo.getCuadrilla().getNombre());
        URI location = URI.create("/forest_access/api/historico_tratamientos/" + nuevo.getIdHistorico());
        return ResponseEntity.created(location).body(htr);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HistoricoTratamientoResponse> delete(@PathVariable Integer id) {
        HistoricoTratamientoResponse htdto = service.delete(id);
        return ResponseEntity.ok(htdto);
    }

}
