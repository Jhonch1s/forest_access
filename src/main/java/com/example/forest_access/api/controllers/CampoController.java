package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.services.CampoService;
import com.example.forest_access.dto.CampoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/campos")
public class CampoController {


    private CampoService camposervice;

    public CampoController(CampoService camposervice) {
        this.camposervice = camposervice;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampoDTO>> MostrarCampos(){
        return ResponseEntity.ok(camposervice.MostrarCampos());
    }

    @PostMapping("/create")
    public ResponseEntity<CampoDTO> crearCampo(@RequestBody CampoDTO campo){
        Campo nuevocampo = camposervice.createCampo(campo);
        URI location = URI.create("/forest_access/api/campos/" + nuevocampo.getIdCampo());
        return ResponseEntity.created(location).body(campo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CampoDTO> actualizarCampo(@PathVariable Integer id,
                                             @RequestBody CampoDTO campo){
        return  ResponseEntity.ok(camposervice.updateCampo(id, campo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CampoDTO> borrarCampo(@PathVariable Integer id){
        return  ResponseEntity.ok(camposervice.deleteCampo(id));
    }


}
