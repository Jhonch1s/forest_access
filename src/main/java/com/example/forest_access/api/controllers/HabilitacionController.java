package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.services.HabilitacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/habilitaciones")
public class HabilitacionController {

    private HabilitacionService service;

    public HabilitacionController(HabilitacionService service) {
        this.service = service;
    }

    public ResponseEntity<List<Habilitacion>> getHabilitaciones(){
        return ResponseEntity.ok(service.mostrarHabilitaciones());
    }

    public ResponseEntity <Habilitacion> crearHabilitacion(@RequestBody Habilitacion habilitacion){
        Habilitacion habilitacionnueva = service.createHabilitacion(habilitacion);
        URI location = URI.create("/forest_access/api/habilitaciones/" + habilitacionnueva.getIdHabilitacion());
        return ResponseEntity.created(location).body(habilitacionnueva);

    }

}
