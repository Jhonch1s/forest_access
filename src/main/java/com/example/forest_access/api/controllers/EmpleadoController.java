package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.EmpleadoResponse;
import com.example.forest_access.api.controllers.response.PaginadoEmpleado;
import com.example.forest_access.biz.dao.services.EmpleadoService;
import com.example.forest_access.dto.EmpleadoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@AllArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> findAll() {
        return ResponseEntity.ok(service.getAllEmpleadosConDias());
    }

    @GetMapping("/paginado/{offset}/{limite}/{filtro}")
    public ResponseEntity<PaginadoEmpleado> findAllPaginado(@PathVariable Integer offset,
                                                            @PathVariable Integer limite,
                                                            @PathVariable Boolean filtro) {
        return ResponseEntity.ok(service.obtenerEmpleadosPaginados(offset,limite,filtro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmpleadoResponse> create(@RequestBody EmpleadoDTO dto) {
        EmpleadoResponse creado = service.create(dto);
        URI location = URI.create("/api/empleados/" + creado.getIdEmpleado());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> update(
            @PathVariable Integer id,
            @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}