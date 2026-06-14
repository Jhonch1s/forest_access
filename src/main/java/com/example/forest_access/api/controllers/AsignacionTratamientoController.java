package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.AsignacionTratamientoRequest;
import com.example.forest_access.api.controllers.response.AsignacionTratamientoPaginado;
import com.example.forest_access.api.controllers.response.AsignacionTratamientoResponse;
import com.example.forest_access.biz.dao.services.AsignacionTratamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones-tratamiento")
@RequiredArgsConstructor
public class AsignacionTratamientoController {
    private final AsignacionTratamientoService service;

    @GetMapping
    public ResponseEntity<List<AsignacionTratamientoResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/parcela/{idParcela}")
    public ResponseEntity<List<AsignacionTratamientoResponse>> getByParcela(@PathVariable Long idParcela) {
        return ResponseEntity.ok(service.getByParcela(idParcela));
    }

    @GetMapping("/parcela/{idParcela}/{offset}/{limite}")
    public ResponseEntity<AsignacionTratamientoPaginado> getByParcelaPaginado(@PathVariable Long idParcela,
                                                                              @PathVariable Integer offset,
                                                                              @PathVariable Integer limite) {
        return ResponseEntity.ok(service.getByParcelaPaginado(idParcela, offset, limite));
    }

    @GetMapping("/rodal/{idRodal}")
    public ResponseEntity<List<AsignacionTratamientoResponse>> getByRodal(@PathVariable Long idRodal) {
        return ResponseEntity.ok(service.getByRodal(idRodal));
    }

    @PostMapping("/create")
    public ResponseEntity<List<AsignacionTratamientoResponse>> create(@RequestBody AsignacionTratamientoRequest dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionTratamientoResponse> update(@PathVariable Long id, @RequestBody AsignacionTratamientoRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<AsignacionTratamientoResponse> iniciarEjecucion(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciarEjecucion(id));
    }
}
