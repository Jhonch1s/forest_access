package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.services.HabilitacionService;
import com.example.forest_access.dto.HabilitacionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/habilitaciones")
public class HabilitacionController {

    private HabilitacionService service;

    public HabilitacionController(HabilitacionService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HabilitacionDTO>> getHabilitaciones(){
        return ResponseEntity.ok(service.mostrarHabilitaciones());
    }


    @PostMapping("/create")
    public ResponseEntity <HabilitacionDTO> crearHabilitacion(@RequestBody HabilitacionDTO habilitacion){
        Habilitacion habilitacionnueva = service.createHabilitacion(habilitacion);
        URI location = URI.create("/forest_access/api/habilitaciones/" + habilitacionnueva.getIdHabilitacion());
        return ResponseEntity.created(location).body(habilitacion);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity <HabilitacionDTO>  actualizarHabilitacion(
            @PathVariable Integer id,
            @RequestBody HabilitacionDTO habilitacion){
        HabilitacionDTO h = service.updateHabilitacion(id,habilitacion);
        return ResponseEntity.ok(h);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>  eliminarHabilitacion(@PathVariable Integer id){
        service.deleteHabilitacion(id);
        return ResponseEntity.noContent().build();
    }



}
