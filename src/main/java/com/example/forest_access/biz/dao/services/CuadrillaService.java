package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.CuadrillaResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoCuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.dto.CuadrillaDTO;
import com.example.forest_access.api.controllers.request.EmpleadoRequest;
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
import com.example.forest_access.api.controllers.response.PaginadoCuadrilla;

@Service
@AllArgsConstructor
public class CuadrillaService {

    private final CuadrillaRepository repository;
    private final EmpleadoCuadrillaRepository empleadoCuadrillaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<CuadrillaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuadrillaResponse findById(Integer id) {
        Cuadrilla cuadrilla = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada con Id: " + id));
        return mapToResponse(cuadrilla);
    }

    @Transactional(readOnly = true)
    public List<CuadrillaResponse> findActivas() {
        return repository.findByActiva(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginadoCuadrilla obtenerCuadrillasPaginadas(Integer offset, Integer limite, Boolean activa) {
        int pageNumber = offset / limite;
        Pageable pageable = PageRequest.of(pageNumber, limite);
        Page<Cuadrilla> pageResult;
        
        if (activa != null) {
            pageResult = repository.findByActiva(activa, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        PaginadoCuadrilla pc = new PaginadoCuadrilla();
        pc.setCuadrillas(pageResult.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
        pc.setTotal((int) pageResult.getTotalElements());
        pc.setPagina(offset);
        pc.setLimite(limite);
        return pc;
    }

    @Transactional
    public CuadrillaResponse create(CuadrillaDTO dto) {
        if (repository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuadrilla con el nombre: " + dto.getNombre());
        }

        Cuadrilla nueva = new Cuadrilla();
        nueva.setNombre(dto.getNombre());
        nueva.setActiva(dto.getActiva() != null ? dto.getActiva() : true);

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public CuadrillaResponse update(Integer id, CuadrillaDTO dto) {
        Cuadrilla existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la cuadrilla"));

        if (!existente.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (repository.findByNombre(dto.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + dto.getNombre() + "' ya está en uso.");
            }
        }

        existente.setNombre(dto.getNombre());
        existente.setActiva(dto.getActiva());

        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No encontrado");
        repository.deleteById(id);
    }

    @Transactional
    public void terminar(Integer id) {
        Cuadrilla cuadrilla = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada"));

        cuadrilla.setActiva(false);

        List<EmpleadoCuadrilla> activos = empleadoCuadrillaRepository.findByCuadrillaAndFechaFinIsNull(cuadrilla);
        for (EmpleadoCuadrilla ec : activos) {
            ec.setFechaFin(LocalDate.now());
        }
        empleadoCuadrillaRepository.saveAll(activos);
    }

    @Transactional
    public void sincronizarEmpleados(Integer idCuadrilla, List<EmpleadoRequest> nuevosMiembros) {
        Cuadrilla cuadrilla = repository.findById(idCuadrilla)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada"));

        List<EmpleadoCuadrilla> activos = empleadoCuadrillaRepository.findByCuadrillaAndFechaFinIsNull(cuadrilla);

        for (EmpleadoCuadrilla ec : activos) {
            boolean sigueEstando = nuevosMiembros.stream()
                    .anyMatch(n -> n.getIdEmpleado().equals(ec.getId().getIdEmpleado()));
            
            if (!sigueEstando) {
                ec.setFechaFin(LocalDate.now());
            }
        }

        for (EmpleadoRequest dto : nuevosMiembros) {
            EmpleadoCuadrilla ecExistente = activos.stream()
                    .filter(ec -> ec.getId().getIdEmpleado().equals(dto.getIdEmpleado()))
                    .findFirst()
                    .orElse(null);

            if (ecExistente != null) {
                ecExistente.setRol(dto.getRol());
            } else {
                Empleado empleado = empleadoRepository.findById(dto.getIdEmpleado())
                        .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado: " + dto.getIdEmpleado()));
                
                EmpleadoCuadrilla nuevoEc = new EmpleadoCuadrilla();
                nuevoEc.setId(new EmpleadoCuadrillaId(idCuadrilla, dto.getIdEmpleado(), LocalDate.now()));
                nuevoEc.setCuadrilla(cuadrilla);
                nuevoEc.setEmpleado(empleado);
                nuevoEc.setRol(dto.getRol());
                nuevoEc.setFechaFin(null);
                
                empleadoCuadrillaRepository.save(nuevoEc);
            }
        }
        
        empleadoCuadrillaRepository.saveAll(activos);
    }

    private CuadrillaResponse mapToResponse(Cuadrilla entidad) {
        CuadrillaResponse res = new CuadrillaResponse();
        res.setIdCuadrilla(entidad.getIdCuadrilla());
        res.setNombre(entidad.getNombre());
        res.setActiva(entidad.getActiva());
        return res;
    }
}