package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.services.TratamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/tratamientos")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tratamiento>> mostrarTratamientos() {
        List<Tratamiento> tratamientos = tratamientoService.findAll();
        return ResponseEntity.ok(tratamientos);
    }

    @PostMapping("/create")
    public ResponseEntity<Tratamiento> createTratamiento(@RequestBody Tratamiento tratamiento) {
        Tratamiento nuevoTratamiento = tratamientoService.create(tratamiento);
        URI location = URI.create("/forest_access/api/tratamientos/" + nuevoTratamiento.getIdTratamiento());
        return ResponseEntity.created(location).body(nuevoTratamiento);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(
            @PathVariable Integer id,
            @RequestBody Tratamiento tratamiento) {
        Tratamiento tratamientoActualizado = tratamientoService.update(id, tratamiento);
        return ResponseEntity.ok(tratamientoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Integer id) {
        tratamientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
