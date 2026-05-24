package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.TareaRequest;
import com.example.forest_access.api.controllers.response.TareaResponse;
import com.example.forest_access.biz.dao.services.TareaService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@AllArgsConstructor
public class TareaController {

    private final TareaService service;

    @GetMapping
    public ResponseEntity<List<TareaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TareaResponse> create(@RequestBody TareaRequest request) {
        TareaResponse creada = service.create(request);
        return ResponseEntity.created(URI.create("/api/tareas/" + creada.getIdTarea())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> update(@PathVariable Integer id, @RequestBody TareaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<TareaResponse>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findPorEmpleado(idEmpleado));
    }

    @GetMapping("/asignacion/{idAsignacion}")
    public ResponseEntity<List<TareaResponse>> findByAsignacion(@PathVariable Long idAsignacion) {
        return ResponseEntity.ok(service.findPorAsignacion(idAsignacion));
    }

    @GetMapping("/liquidacion")
    public ResponseEntity<List<TareaResponse>> findParaLiquidacion(
            @RequestParam Integer idEmpleado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(service.findParaLiquidacion(idEmpleado, inicio, hasta));
    }
}
