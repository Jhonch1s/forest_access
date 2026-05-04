package com.example.forest_access.api.controllers;


import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.repositories.EstadoRepository;
import com.example.forest_access.biz.dao.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/forest_access/api/estados")
public class EstadoController {

    private EstadoService estadoservice;

    public EstadoController(EstadoService estadoservice){
        this.estadoservice = estadoservice;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Estado>> conseguirEstados() {
        return ResponseEntity.ok(estadoservice.MostrarEstados());
    }


}
