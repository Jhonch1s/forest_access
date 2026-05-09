package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.CatalogoTareaRequest;
import com.example.forest_access.api.controllers.response.CatalogoTareaResponse;
import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.CatalogoTareaRepository;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
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
    public CatalogoTareaResponse create(CatalogoTareaRequest request) {
        if (repository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una tarea con el nombre: " + request.getNombre());
        }

        CatalogoTarea nueva = new CatalogoTarea();
        updateEntityFromRequest(nueva, request);

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public CatalogoTareaResponse update(Integer id, CatalogoTareaRequest request) {
        CatalogoTarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la tarea"));

        if (!existente.getNombre().equalsIgnoreCase(request.getNombre())) {
            if (repository.findByNombre(request.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + request.getNombre() + "' ya está en uso.");
            }
        }

        updateEntityFromRequest(existente, request);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No encontrado");
        repository.deleteById(id);
    }

    // --- MAPPERS ---

    private void updateEntityFromRequest(CatalogoTarea entidad, CatalogoTareaRequest request) {
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());

        if (request.getIdHabilitacion() != null) {
            Habilitacion h = habilitacionRepository.findById(request.getIdHabilitacion())
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