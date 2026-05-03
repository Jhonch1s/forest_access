package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Tarea;
import com.example.forest_access.biz.dao.services.TareaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/tareas")
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Tarea> create(@RequestBody Tarea tarea) {
        Tarea creada = service.create(tarea);
        URI location = URI.create("/forest_access/api/tareas/" + creada.getIdTarea());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> update(@PathVariable Integer id, @RequestBody Tarea datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<Tarea>> findByEmpleado(@PathVariable Integer idEmpleado) {
        return ResponseEntity.ok(service.findPorEmpleado(idEmpleado));
    }

    @GetMapping("/estado/{nombreEstado}")
    public ResponseEntity<List<Tarea>> findByEstado(@PathVariable String nombreEstado) {
        return ResponseEntity.ok(service.findPorEstado(nombreEstado));
    }

    @GetMapping("/parcela/{idParcela}")
    public ResponseEntity<List<Tarea>> findByParcela(@PathVariable Integer idParcela) {
        return ResponseEntity.ok(service.findPorParcela(idParcela));
    }

    @GetMapping("/liquidacion")
    public ResponseEntity<List<Tarea>> findParaLiquidacion(
            @RequestParam Integer idEmpleado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);

        return ResponseEntity.ok(service.findParaLiquidacion(empleado, inicio, hasta));
    }
}