package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.CatalogoTareaResponse;
import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.CatalogoTareaRepository;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
import com.example.forest_access.dto.CatalogoTareaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatalogoTareaService {

    private final CatalogoTareaRepository repository;
    private final HabilitacionRepository habilitacionRepository;

    @Transactional(readOnly = true)
    public List<CatalogoTareaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CatalogoTareaResponse findById(Integer id) {
        CatalogoTarea tarea = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + id));
        return mapToResponse(tarea);
    }

    @Transactional
    public CatalogoTareaResponse create(CatalogoTareaDTO dto) {
        if (repository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una tarea con el nombre: " + dto.getNombre());
        }

        CatalogoTarea nueva = new CatalogoTarea();
        updateEntityFromDTO(nueva, dto);

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public CatalogoTareaResponse update(Integer id, CatalogoTareaDTO dto) {
        CatalogoTarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la tarea"));

        if (!existente.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (repository.findByNombre(dto.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + dto.getNombre() + "' ya está en uso.");
            }
        }

        updateEntityFromDTO(existente, dto);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No encontrado");
        repository.deleteById(id);
    }

    private void updateEntityFromDTO(CatalogoTarea entidad, CatalogoTareaDTO dto) {
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());

        if (dto.getRequiereHabilitacion() != null && dto.getRequiereHabilitacion().getIdHabilitacion() != null) {
            Habilitacion h = habilitacionRepository.findById(dto.getRequiereHabilitacion().getIdHabilitacion())
                    .orElseThrow(() -> new EntityNotFoundException("Habilitación no encontrada"));
            entidad.setRequiereHabilitacion(h);
        } else {
            entidad.setRequiereHabilitacion(null);
        }
    }

    private CatalogoTareaResponse mapToResponse(CatalogoTarea entidad) {
        CatalogoTareaResponse res = new CatalogoTareaResponse();
        res.setIdCatalogoTarea(entidad.getIdCatalogoTarea());
        res.setNombre(entidad.getNombre());
        res.setDescripcion(entidad.getDescripcion());

        if (entidad.getRequiereHabilitacion() != null) {
            res.setIdHabilitacion(entidad.getRequiereHabilitacion().getIdHabilitacion());
            res.setNombreHabilitacion(entidad.getRequiereHabilitacion().getNombre());
        }
        return res;
    }
}