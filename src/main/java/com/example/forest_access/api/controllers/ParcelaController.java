package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.services.ParcelaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/parcelas")
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
    public ResponseEntity <Parcela> crearParcela(@RequestBody Parcela parcela){
        Parcela creada = parcelaservice.createParcela(parcela);
        URI location = URI.create("/forest_access/api/parcelas/" + creada.getIdParcela());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <Parcela> actualizarParcela(@PathVariable Integer id,
                                                      @RequestBody Parcela parcela){
        return ResponseEntity.ok(parcelaservice.updateParcela(id,parcela));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Parcela> eliminarParcela(@PathVariable Integer id){
        return ResponseEntity.ok(parcelaservice.deleteParcela(id));
    }





}
