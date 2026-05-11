package com.example.forest_access.api.controllers;


import com.example.forest_access.api.controllers.response.RodalResponse;
import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.services.RodalService;
import com.example.forest_access.dto.RodalDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rodales")
public class RodalController {

    private RodalService rodalService;

    public RodalController(RodalService rodalService) {
        this.rodalService = rodalService;
    }

    @GetMapping("/all")
    public ResponseEntity <List<RodalResponse>> mostrarRodales(){
        return ResponseEntity.ok(rodalService.mostrarRodales());
    }

    @PostMapping("/create")
    public ResponseEntity <RodalDTO> crearRodal(@RequestBody RodalDTO rodal){
        Rodal nuevoRodal = rodalService.createRodal(rodal);
        RodalResponse r = new RodalResponse();
        BeanUtils.copyProperties(nuevoRodal, r);
        r.setNombreCampo(nuevoRodal.getNombre());
        URI location = URI.create("/forest_access/api/rodales/" + nuevoRodal.getIdRodal());
        return ResponseEntity.created(location).body(rodal);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <RodalResponse> actualizarRodal(@PathVariable Integer id,
                                                  @RequestBody RodalDTO rodal){
        return ResponseEntity.ok(rodalService.updateRodal(id,rodal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <RodalResponse> borrarRodal(@PathVariable Integer id){
        return ResponseEntity.ok(rodalService.deleteRodal(id));
    }



}
