package com.example.forest_access.biz.dao.services;


import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import com.example.forest_access.biz.dao.repositories.EmpleadoHabilitacionRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
import com.example.forest_access.dto.EmpleadoHabilitacionDTO;
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
    private EmpleadoRepository empleadorepository;
    private HabilitacionRepository habilitacionrepository;



    @Transactional
    public EmpleadoHabilitacion findById(EmpleadoHabilitacionId id) {
        return empleadoHabilitacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación Empleado-Habilitacion no encontrada"));
    }

    @Transactional
    public List<EmpleadoHabilitacionDTO> getHabilitacionesEmp(){

        return empleadoHabilitacionRepository.findAll().stream().map( eh ->{
            EmpleadoHabilitacionDTO empleadohabilitacion = new EmpleadoHabilitacionDTO();
            empleadohabilitacion.setIdEmpleado(eh.getEmpleado().getIdEmpleado());
            empleadohabilitacion.setIdHabilitacion(eh.getHabilitacion().getIdHabilitacion());
            empleadohabilitacion.setFechaEmision(eh.getFechaEmision());
            empleadohabilitacion.setFechaVencimiento(eh.getFechaVencimiento());
            return  empleadohabilitacion;
        }).toList();
    }

    @Transactional
    public EmpleadoHabilitacion createHabilitacionEmp(EmpleadoHabilitacionDTO relacion){
        if(relacion.getIdEmpleado() == null || relacion.getIdHabilitacion() == null ){
            throw new RuntimeException("Id de empleado o habilitacion vacios");
        }
        Empleado empleado = empleadorepository.findById(relacion.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("No existe el empleado"));
        Habilitacion habilitacion = habilitacionrepository.findById(relacion.getIdHabilitacion())
                .orElseThrow(() -> new RuntimeException("No existe la habilitacion"));
        EmpleadoHabilitacionId id =  new EmpleadoHabilitacionId(relacion.getIdEmpleado(),relacion.getIdHabilitacion());
        EmpleadoHabilitacion eh = findById(id);
        if(eh != null){
            throw new RuntimeException("Esa habilitacion para el empleado: " + eh.getEmpleado().getNombre()+ " ya existe");
        }else{
            EmpleadoHabilitacion nuevaEH = new EmpleadoHabilitacion();

            nuevaEH.setEmpleado(empleado);
            nuevaEH.setHabilitacion(habilitacion);
            nuevaEH.setFechaEmision(relacion.getFechaEmision());
            nuevaEH.setFechaVencimiento(relacion.getFechaVencimiento());
            empleadoHabilitacionRepository.save(nuevaEH);
            return nuevaEH;
        }


    }

    @Transactional
    public EmpleadoHabilitacionDTO deleteHabilitacionEmp(EmpleadoHabilitacionId id){
        if(!empleadoHabilitacionRepository.existsById(id)){
            throw new EntityNotFoundException("No existe la asignación a eliminar");
        }
        EmpleadoHabilitacion emphab = findById(id);
        EmpleadoHabilitacionDTO emphab1 = new EmpleadoHabilitacionDTO();
        emphab1.setIdEmpleado(emphab.getEmpleado().getIdEmpleado());
        emphab1.setIdHabilitacion(emphab.getHabilitacion().getIdHabilitacion());
        emphab1.setFechaEmision(emphab.getFechaEmision());
        emphab1.setFechaVencimiento(emphab.getFechaVencimiento());
        empleadoHabilitacionRepository.deleteById(id);
        return emphab1;
    }

}
