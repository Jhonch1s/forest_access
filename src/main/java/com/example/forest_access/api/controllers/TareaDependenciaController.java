package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.entities.TareaDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TareaDependenciaId;
import com.example.forest_access.biz.dao.services.TareaDependenciaService;
import com.example.forest_access.dto.TareaDependenciaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tarea_dependencias")
public class TareaDependenciaController {

    private TareaDependenciaService service;

    public TareaDependenciaController(TareaDependenciaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TareaDependenciaDTO>> getTareaDependencias() {
        return ResponseEntity.ok(service.mostrarTareaDependencias());
    }

    @PostMapping("/create")
    public ResponseEntity<TareaDependenciaDTO> crearTareaDependencia(@RequestBody
                                                                  TareaDependenciaDTO tareaDependencia) {
        TareaDependencia tarea = service.crearTareaDependencia(tareaDependencia);
        URI location = URI.create("/forest_access/api/tarea_dependencias/" + tarea.getId());
        return ResponseEntity.created(location).body(tareaDependencia);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<TareaDependenciaDTO> borrarDependencia(@RequestParam Integer id_anterior,
                                                              @RequestParam Integer id_posterior){
        TareaDependenciaId id = new TareaDependenciaId(id_anterior,id_posterior);
        TareaDependenciaDTO td= service.deleteTareaDependencia(id);
        return ResponseEntity.ok(td);
    }
}
