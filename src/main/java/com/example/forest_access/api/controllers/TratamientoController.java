package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.services.TratamientoService;
import com.example.forest_access.dto.TratamientoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TratamientoDTO>> mostrarTratamientos() {
        List<TratamientoDTO> tratamientos = tratamientoService.findAll();
        return ResponseEntity.ok(tratamientos);
    }

    @PostMapping("/create")
    public ResponseEntity<TratamientoDTO> createTratamiento(@RequestBody TratamientoDTO tratamiento) {
        Tratamiento nuevoTratamiento = tratamientoService.create(tratamiento);
        URI location = URI.create("/forest_access/api/tratamientos/" + nuevoTratamiento.getIdTratamiento());
        return ResponseEntity.created(location).body(tratamiento);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TratamientoDTO> updateTratamiento(
            @PathVariable Integer id,
            @RequestBody TratamientoDTO tratamiento) {
        TratamientoDTO tratamientoActualizado = tratamientoService.update(id, tratamiento);
        return ResponseEntity.ok(tratamientoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TratamientoDTO> deleteTratamiento(@PathVariable Integer id) {
        TratamientoDTO t = tratamientoService.delete(id);
        return ResponseEntity.ok(t);
    }
}
