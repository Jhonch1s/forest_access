package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.TratamientoDependencia;
import com.example.forest_access.biz.dao.services.TratamientoDependenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/tratamientos-dependencias")
public class TratamientoDependenciaController {

    private final TratamientoDependenciaService service;

    public TratamientoDependenciaController(TratamientoDependenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TratamientoDependencia>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<TratamientoDependencia> findById(
            @RequestParam Integer idPosterior,
            @RequestParam Integer idAnterior) {
        return ResponseEntity.ok(service.findById(idPosterior, idAnterior));
    }

    @PostMapping("/create")
    public ResponseEntity<TratamientoDependencia> create(@RequestBody TratamientoDependencia dependencia) {
        TratamientoDependencia creada = service.create(dependencia);
        return ResponseEntity.created(URI.create("/forest_access/api/tratamientos-dependencias")).body(creada);
    }

    @PutMapping("/update")
    public ResponseEntity<TratamientoDependencia> update(
            @RequestParam Integer idPosterior,
            @RequestParam Integer idAnterior,
            @RequestBody TratamientoDependencia datos) {
        return ResponseEntity.ok(service.update(idPosterior, idAnterior, datos));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
            @RequestParam Integer idPosterior,
            @RequestParam Integer idAnterior) {
        service.delete(idPosterior, idAnterior);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/requisitos/{idPosterior}")
    public ResponseEntity<List<TratamientoDependencia>> getRequisitos(@PathVariable Integer idPosterior) {
        return ResponseEntity.ok(service.findPorTratamientoPosterior(idPosterior));
    }

    @GetMapping("/bloqueados/{idAnterior}")
    public ResponseEntity<List<TratamientoDependencia>> getBloqueados(@PathVariable Integer idAnterior) {
        return ResponseEntity.ok(service.findPorTratamientoAnterior(idAnterior));
    }
}