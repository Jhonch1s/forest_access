package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.CuadrillaResponse;
import com.example.forest_access.biz.dao.services.CuadrillaService;
import com.example.forest_access.dto.CuadrillaDTO;
import com.example.forest_access.api.controllers.request.EmpleadoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cuadrillas")
@AllArgsConstructor
public class CuadrillaController {

    private final CuadrillaService service;

    @GetMapping
    public ResponseEntity<List<CuadrillaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuadrillaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CuadrillaResponse>> findActivas() {
        return ResponseEntity.ok(service.findActivas());
    }

    @PostMapping("/create")
    public ResponseEntity<CuadrillaResponse> create(@RequestBody CuadrillaDTO dto) {
        CuadrillaResponse creada = service.create(dto);
        return ResponseEntity.created(URI.create("/api/cuadrillas/" + creada.getIdCuadrilla())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuadrillaResponse> update(
            @PathVariable Integer id,
            @RequestBody CuadrillaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/terminar")
    public ResponseEntity<Void> terminar(@PathVariable Integer id) {
        service.terminar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/sincronizar-empleados")
    public ResponseEntity<Void> sincronizarEmpleados(
            @PathVariable Integer id,
            @RequestBody List<EmpleadoRequest> empleados) {
        service.sincronizarEmpleados(id, empleados);
        return ResponseEntity.ok().build();
    }
}