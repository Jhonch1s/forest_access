package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.TareaAsignadaRequest;
import com.example.forest_access.api.controllers.response.TareaAsignadaResponse;
import com.example.forest_access.biz.dao.services.TareaAsignadaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tareas-asignadas")
@AllArgsConstructor
public class TareaAsignadaController {

    private final TareaAsignadaService service;

    @GetMapping
    public ResponseEntity<List<TareaAsignadaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaAsignadaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/cuadrilla/{idCuadrilla}/vigentes")
    public ResponseEntity<List<TareaAsignadaResponse>> findByCuadrillaVigentes(@PathVariable Integer idCuadrilla) {
        return ResponseEntity.ok(service.findByCuadrillaVigentes(idCuadrilla));
    }

    @GetMapping("/cuadrilla/{idCuadrilla}/asignacion/{idAsignacion}")
    public ResponseEntity<List<TareaAsignadaResponse>> findByAsignacionAndCuadrilla(
            @PathVariable Long idAsignacion,
            @PathVariable Integer idCuadrilla) {
        return ResponseEntity.ok(service.findByAsignacionAndCuadrillaVigentes(idAsignacion, idCuadrilla));
    }

    @PostMapping("/create")
    public ResponseEntity<TareaAsignadaResponse> create(@RequestBody TareaAsignadaRequest request) {
        TareaAsignadaResponse creada = service.create(request);
        return ResponseEntity.created(URI.create("/api/tareas-asignadas/" + creada.getIdTareaAsignada())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaAsignadaResponse> update(
            @PathVariable Integer id,
            @RequestBody TareaAsignadaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
