package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.EmpleadoCuadrillaResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoCuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.dto.EmpleadoCuadrillaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.forest_access.api.controllers.response.PaginadoEmpleadoCuadrilla;

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
    public EmpleadoCuadrillaResponse create(EmpleadoCuadrillaDTO dto) {
        Cuadrilla cuadrilla = cuadrillaRepository.findById(dto.getCuadrilla().getIdCuadrilla())
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada"));
        Empleado empleado = empleadoRepository.findById(dto.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        LocalDate fechaIni = LocalDate.now();
        EmpleadoCuadrillaId id = new EmpleadoCuadrillaId(cuadrilla.getIdCuadrilla(), empleado.getIdEmpleado(), fechaIni);

        if (repository.existsById(id)) {
            throw new IllegalArgumentException("El empleado ya tiene esta asignación registrada para la fecha actual.");
        }

        EmpleadoCuadrilla relacion = new EmpleadoCuadrilla();
        relacion.setId(id);
        relacion.setCuadrilla(cuadrilla);
        relacion.setEmpleado(empleado);
        relacion.setFechaFin(dto.getFechaFin());
        relacion.setRol(dto.getRol()); // Faltaba esto!
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

    @Transactional(readOnly = true)
    public PaginadoEmpleadoCuadrilla obtenerEmpleadosPaginadosPorCuadrilla(Integer idCuadrilla, Integer offset, Integer limite, Boolean mostrarHistorial) {
        int pageNumber = offset / limite;
        Pageable pageable = PageRequest.of(pageNumber, limite);
        Page<EmpleadoCuadrilla> pageResult;

        if (mostrarHistorial != null && mostrarHistorial) {
            pageResult = repository.findByCuadrilla_IdCuadrilla(idCuadrilla, pageable);
        } else {
            pageResult = repository.findByCuadrilla_IdCuadrillaAndFechaFinIsNull(idCuadrilla, pageable);
        }

        PaginadoEmpleadoCuadrilla pec = new PaginadoEmpleadoCuadrilla();
        pec.setEmpleadosCuadrilla(pageResult.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
        pec.setTotal((int) pageResult.getTotalElements());
        pec.setPagina(offset);
        pec.setLimite(limite);
        return pec;
    }

    private EmpleadoCuadrillaResponse mapToResponse(EmpleadoCuadrilla entidad) {
        EmpleadoCuadrillaResponse res = new EmpleadoCuadrillaResponse();
        res.setIdCuadrilla(entidad.getId().getIdCuadrilla());
        res.setNombreCuadrilla(entidad.getCuadrilla().getNombre());
        res.setIdEmpleado(entidad.getId().getIdEmpleado());
        res.setNombreEmpleado(entidad.getEmpleado().getNombre());
        res.setFechaInicio(entidad.getId().getFechaInicio());
        res.setFechaFin(entidad.getFechaFin());
        res.setRol(entidad.getRol());
        res.setEsActivo(entidad.getFechaFin() == null);

        return res;
    }
}