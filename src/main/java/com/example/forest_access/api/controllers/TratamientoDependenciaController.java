package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.TratamientoDependenciaResponse;
import com.example.forest_access.biz.dao.services.TratamientoDependenciaService;
import com.example.forest_access.dto.TratamientoDependenciaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos-dependencias")
@AllArgsConstructor
public class TratamientoDependenciaController {

    private final TratamientoDependenciaService service;

    @GetMapping
    public ResponseEntity<List<TratamientoDependenciaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<TratamientoDependenciaResponse> create(@RequestBody TratamientoDependenciaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestParam Integer idAnterior,
            @RequestParam Integer idPosterior) {
        service.delete(idAnterior, idPosterior);
        return ResponseEntity.noContent().build();
    }
}