package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.request.TareaRequest;
import com.example.forest_access.api.controllers.response.TareaResponse;
import com.example.forest_access.biz.dao.services.TareaService;
import com.example.forest_access.dto.ReporteEmpleadoDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/reporte-batch")
    public ResponseEntity<?> getReporteBatch(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<ReporteEmpleadoDTO> fullList = service.getReporteBatch(inicio, hasta);
        if (page != null && size != null && size > 0) {
            int start = page * size;
            int end = Math.min(start + size, fullList.size());
            List<ReporteEmpleadoDTO> content = start >= fullList.size() ? List.of() : fullList.subList(start, end);
            Page<ReporteEmpleadoDTO> pageResult = new PageImpl<>(content, PageRequest.of(page, size), fullList.size());
            return ResponseEntity.ok(pageResult);
        }
        return ResponseEntity.ok(fullList);
    }
}
