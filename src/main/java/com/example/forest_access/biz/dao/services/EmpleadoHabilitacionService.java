package com.example.forest_access.biz.dao.services;


import com.example.forest_access.api.controllers.response.EmpleadoHabilitacionResponse;
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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public List<EmpleadoHabilitacionResponse> getHabilitacionesEmp(){

        return empleadoHabilitacionRepository.findAll().stream().map( eh ->{
            EmpleadoHabilitacionResponse empleadohabilitacion = new EmpleadoHabilitacionResponse();
            empleadohabilitacion.setIdEmpleado(eh.getEmpleado().getIdEmpleado());
            empleadohabilitacion.setIdHabilitacion(eh.getHabilitacion().getIdHabilitacion());
            empleadohabilitacion.setNombreEmpleado(eh.getEmpleado().getNombre());
            empleadohabilitacion.setNombreHabilitacion(eh.getHabilitacion().getNombre());
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
        boolean existe = empleadoHabilitacionRepository.existsById(id);
        if(existe){
            EmpleadoHabilitacion eh = findById(id);
            throw new RuntimeException("Esa habilitacion para el empleado: " + eh.getEmpleado().getNombre()+ " ya existe");
        }else{
            EmpleadoHabilitacion nuevaEH = new EmpleadoHabilitacion();

            EmpleadoHabilitacionId id1 = new EmpleadoHabilitacionId(relacion.getIdEmpleado(),relacion.getIdHabilitacion());
            nuevaEH.setId(id1);
            nuevaEH.setEmpleado(empleado);
            nuevaEH.setHabilitacion(habilitacion);
            nuevaEH.setFechaEmision(relacion.getFechaEmision());
            nuevaEH.setFechaVencimiento(relacion.getFechaVencimiento());
            empleadoHabilitacionRepository.save(nuevaEH);
            return nuevaEH;
        }


    }
    @Transactional
    public EmpleadoHabilitacionResponse updateHabilitacionEmp(Integer idEmp,Integer idHab, EmpleadoHabilitacionDTO relacion){
        if(idEmp == null || idHab == null ){
            throw new RuntimeException("Id de empleado o habilitacion vacios");
        }
        Empleado empleado = empleadorepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("No existe el empleado"));
        Habilitacion habilitacion = habilitacionrepository.findById(idHab)
                .orElseThrow(() -> new RuntimeException("No existe la habilitacion"));
        EmpleadoHabilitacionId id1 = new EmpleadoHabilitacionId(idEmp,idHab);
        EmpleadoHabilitacion nuevaEH = empleadoHabilitacionRepository.findById(id1)
                        .orElseThrow( () -> new RuntimeException("No existe la relacion"));
        nuevaEH.setEmpleado(empleado);
        nuevaEH.setHabilitacion(habilitacion);
        nuevaEH.setFechaEmision(relacion.getFechaEmision());
        nuevaEH.setFechaVencimiento(relacion.getFechaVencimiento());
        empleadoHabilitacionRepository.save(nuevaEH);
        EmpleadoHabilitacionResponse resp = new EmpleadoHabilitacionResponse();
        BeanUtils.copyProperties(nuevaEH, resp);
        resp.setNombreEmpleado(empleado.getNombre());
        resp.setNombreHabilitacion(habilitacion.getNombre());
        return resp;

    }


    @Transactional
    public EmpleadoHabilitacionResponse deleteHabilitacionEmp(EmpleadoHabilitacionId id){
        if(!empleadoHabilitacionRepository.existsById(id)){
            throw new EntityNotFoundException("No existe la asignación a eliminar");
        }
        EmpleadoHabilitacion emphab = findById(id);
        EmpleadoHabilitacionResponse emphab1 = new EmpleadoHabilitacionResponse();
        emphab1.setIdEmpleado(emphab.getEmpleado().getIdEmpleado());
        emphab1.setIdHabilitacion(emphab.getHabilitacion().getIdHabilitacion());
        emphab1.setNombreEmpleado(emphab.getEmpleado().getNombre());
        emphab1.setNombreHabilitacion(emphab.getHabilitacion().getNombre());
        emphab1.setFechaEmision(emphab.getFechaEmision());
        emphab1.setFechaVencimiento(emphab.getFechaVencimiento());
        empleadoHabilitacionRepository.deleteById(id);
        return emphab1;
    }

    @Transactional
    public List<EmpleadoHabilitacionResponse> getHabilitacionesEmpleado(Integer id){
        return empleadoHabilitacionRepository.findAll().stream()
                .filter( he -> Objects.equals(he.getEmpleado().getIdEmpleado(), id))
                .map( he ->{
                    EmpleadoHabilitacionResponse emphab1 = new EmpleadoHabilitacionResponse();
                    emphab1.setIdEmpleado(he.getEmpleado().getIdEmpleado());
                    emphab1.setIdHabilitacion(he.getHabilitacion().getIdHabilitacion());
                    emphab1.setNombreEmpleado(he.getEmpleado().getNombre());
                    emphab1.setNombreHabilitacion(he.getHabilitacion().getNombre());
                    emphab1.setFechaEmision(he.getFechaEmision());
                    emphab1.setFechaVencimiento(he.getFechaVencimiento());
                    return emphab1;
                }).toList();
    }

}
