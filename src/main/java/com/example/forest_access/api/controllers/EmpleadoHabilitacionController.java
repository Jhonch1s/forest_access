package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import com.example.forest_access.biz.dao.services.EmpleadoHabilitacionService;
import com.example.forest_access.dto.EmpleadoHabilitacionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleado_habilitaciones")
public class EmpleadoHabilitacionController {

    private EmpleadoHabilitacionService service;

    public EmpleadoHabilitacionController(EmpleadoHabilitacionService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmpleadoHabilitacionDTO>> getAllHabilitaciones(){
        return ResponseEntity.ok(service.getHabilitacionesEmp());
    }

    @PostMapping("/create")
    public ResponseEntity<EmpleadoHabilitacionDTO> crearRelacion(@RequestBody EmpleadoHabilitacionDTO empHab){
        EmpleadoHabilitacion nuevo = service.createHabilitacionEmp(empHab);
        URI location = URI.create("/forest_access/api/empleado_habilitaciones/"
                + nuevo.getId());
        return ResponseEntity.created(location).body(empHab);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<EmpleadoHabilitacionDTO> borrarEmpleadoHabilitacion(@RequestParam Integer idEmpleado,
                                                           @RequestParam Integer idHabilitacion){
        EmpleadoHabilitacionId id = new EmpleadoHabilitacionId(idEmpleado, idHabilitacion);
        EmpleadoHabilitacionDTO EH = service.deleteHabilitacionEmp(id);
        return ResponseEntity.ok(EH);
    }


}
