package com.example.forest_access.biz.dao.services;


import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import com.example.forest_access.biz.dao.repositories.EmpleadoHabilitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmpleadoHabilitacionService {

    private EmpleadoHabilitacionRepository empleadoHabilitacionRepository;

    @Transactional
    public EmpleadoHabilitacion findById(EmpleadoHabilitacionId id) {
        return empleadoHabilitacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación Empleado-Habilitacion no encontrada"));
    }

    @Transactional
    public List<EmpleadoHabilitacion> getHabilitacionesEmp(){
        return empleadoHabilitacionRepository.findAll();
    }

    @Transactional
    public EmpleadoHabilitacion createHabilitacionEmp(EmpleadoHabilitacion relacion){
        if(relacion.getId() == null){
            relacion.setId(new EmpleadoHabilitacionId(
                    relacion.getEmpleado().getIdEmpleado(),
                    relacion.getHabilitacion().getIdHabilitacion())
            );
        }
        return empleadoHabilitacionRepository.save(relacion);

    }

    @Transactional
    public void deleteHabilitacionEmp(EmpleadoHabilitacionId id){
        if(!empleadoHabilitacionRepository.existsById(id)){
            throw new EntityNotFoundException("No existe la asignación a eliminar");
        }
        empleadoHabilitacionRepository.deleteById(id);
    }

}
