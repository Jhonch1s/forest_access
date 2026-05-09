package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.CuadrillaRequest;
import com.example.forest_access.api.controllers.response.CuadrillaResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CuadrillaService {

    private final CuadrillaRepository repository;

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

    @Transactional
    public CuadrillaResponse create(CuadrillaRequest request) {
        if (repository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuadrilla con el nombre: " + request.getNombre());
        }

        Cuadrilla nueva = new Cuadrilla();
        nueva.setNombre(request.getNombre());
        nueva.setActiva(request.getActiva() != null ? request.getActiva() : true);

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public CuadrillaResponse update(Integer id, CuadrillaRequest request) {
        Cuadrilla existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la cuadrilla"));

        if (!existente.getNombre().equalsIgnoreCase(request.getNombre())) {
            if (repository.findByNombre(request.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + request.getNombre() + "' ya está en uso.");
            }
        }

        existente.setNombre(request.getNombre());
        existente.setActiva(request.getActiva());

        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No encontrado");
        repository.deleteById(id);
    }

    // Mapper para el Response
    private CuadrillaResponse mapToResponse(Cuadrilla entidad) {
        CuadrillaResponse res = new CuadrillaResponse();
        res.setIdCuadrilla(entidad.getIdCuadrilla());
        res.setNombre(entidad.getNombre());
        res.setActiva(entidad.getActiva());
        return res;
    }
}