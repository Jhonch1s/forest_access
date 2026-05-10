package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.CuadrillaResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.dto.CuadrillaDTO;
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

    private CuadrillaResponse mapToResponse(Cuadrilla entidad) {
        CuadrillaResponse res = new CuadrillaResponse();
        res.setIdCuadrilla(entidad.getIdCuadrilla());
        res.setNombre(entidad.getNombre());
        res.setActiva(entidad.getActiva());
        return res;
    }
}