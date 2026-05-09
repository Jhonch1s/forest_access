package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.EmpleadoCuadrillaRequest;
import com.example.forest_access.api.controllers.response.EmpleadoCuadrillaResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoCuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpleadoCuadrillaService {

    private final EmpleadoCuadrillaRepository repository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<EmpleadoCuadrillaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmpleadoCuadrillaResponse create(EmpleadoCuadrillaRequest request) {
        // 1. Buscar entidades
        Cuadrilla cuadrilla = cuadrillaRepository.findById(request.getIdCuadrilla())
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada"));
        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // 2. Crear ID compuesto (si no viene fecha, usamos hoy)
        LocalDate fechaIni = request.getFechaInicio() != null ? request.getFechaInicio() : LocalDate.now();
        EmpleadoCuadrillaId id = new EmpleadoCuadrillaId(cuadrilla.getIdCuadrilla(), empleado.getIdEmpleado(), fechaIni);

        if (repository.existsById(id)) {
            throw new IllegalArgumentException("El empleado ya tiene esta asignación registrada");
        }

        // 3. Guardar
        EmpleadoCuadrilla relacion = new EmpleadoCuadrilla();
        relacion.setId(id);
        relacion.setCuadrilla(cuadrilla);
        relacion.setEmpleado(empleado);
        relacion.setFechaFin(request.getFechaFin());

        return mapToResponse(repository.save(relacion));
    }

    @Transactional
    public void delete(Integer idCuadrilla, Integer idEmpleado, LocalDate fechaInicio) {
        EmpleadoCuadrillaId id = new EmpleadoCuadrillaId(idCuadrilla, idEmpleado, fechaInicio);
        if (!repository.existsById(id)) throw new EntityNotFoundException("No existe la asignación");
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoCuadrillaResponse> findByCuadrilla(Integer idCuadrilla) {
        return repository.findByCuadrilla_IdCuadrilla(idCuadrilla).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EmpleadoCuadrillaResponse mapToResponse(EmpleadoCuadrilla entidad) {
        EmpleadoCuadrillaResponse res = new EmpleadoCuadrillaResponse();
        res.setIdCuadrilla(entidad.getId().getIdCuadrilla());
        res.setNombreCuadrilla(entidad.getCuadrilla().getNombre());
        res.setIdEmpleado(entidad.getId().getIdEmpleado());
        res.setNombreEmpleado(entidad.getEmpleado().getNombre());
        res.setFechaInicio(entidad.getId().getFechaInicio());
        res.setFechaFin(entidad.getFechaFin());
        return res;
    }
}