package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.ParcelaResponse;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.services.ParcelaService;
import com.example.forest_access.dto.ParcelaDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/parcelas")
public class ParcelaController {

    private ParcelaService parcelaservice;

    public ParcelaController(ParcelaService parcelaservice){
        this.parcelaservice = parcelaservice;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parcela>> mostrarParcelas(){
        return ResponseEntity.ok(parcelaservice.mostrarParcelas());
    }

    @PostMapping("/create")
    public ResponseEntity <ParcelaResponse> crearParcela(@RequestBody ParcelaDTO parcela){
        Parcela creada = parcelaservice.createParcela(parcela);
        ParcelaResponse p = new ParcelaResponse();
        BeanUtils.copyProperties(creada, p);
        p.setNombreRodal(creada.getRodal().getNombre());
        URI location = URI.create("/forest_access/api/parcelas/" + creada.getIdParcela());
        return ResponseEntity.created(location).body(p);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <ParcelaResponse> actualizarParcela(@PathVariable Integer id,
                                                      @RequestBody ParcelaDTO parcela){
        return ResponseEntity.ok(parcelaservice.updateParcela(id,parcela));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <ParcelaResponse> eliminarParcela(@PathVariable Integer id){
        return ResponseEntity.ok(parcelaservice.deleteParcela(id));
    }





}
