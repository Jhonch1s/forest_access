package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.PlantillaTareaRequest;
import com.example.forest_access.api.controllers.response.PlantillaTareaResponse;
import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.PlantillaTarea;
import com.example.forest_access.biz.dao.repositories.PlantillaTareaRepository;
import com.example.forest_access.biz.dao.repositories.CatalogoTareaRepository; // Necesario
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlantillaTareaService {

    private final PlantillaTareaRepository repository;
    private final CatalogoTareaRepository catalogoRepository;

    @Transactional(readOnly = true)
    public List<PlantillaTareaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlantillaTareaResponse findById(Integer id) {
        PlantillaTarea plantilla = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada con ID: " + id));
        return mapToResponse(plantilla);
    }

    @Transactional
    public PlantillaTareaResponse create(PlantillaTareaRequest request) {
        if (repository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una plantilla con el nombre: " + request.getNombre());
        }

        PlantillaTarea nueva = new PlantillaTarea();
        updateEntityFromRequest(nueva, request);

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public PlantillaTareaResponse update(Integer id, PlantillaTareaRequest request) {
        PlantillaTarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla no encontrada"));

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
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Plantilla no encontrada");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PlantillaTareaResponse> findByCatalogo(Integer idCatalogo) {
        return repository.findByCatalogoTarea_IdCatalogoTarea(idCatalogo).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Métodos de Mapeo
    private void updateEntityFromRequest(PlantillaTarea entidad, PlantillaTareaRequest request) {
        entidad.setNombre(request.getNombre());
        entidad.setDescripcion(request.getDescripcion());

        if (request.getIdCatalogoTarea() != null) {
            CatalogoTarea cat = catalogoRepository.findById(request.getIdCatalogoTarea())
                    .orElseThrow(() -> new EntityNotFoundException("Catálogo de tarea no encontrado"));
            entidad.setCatalogoTarea(cat);
        }
    }

    private PlantillaTareaResponse mapToResponse(PlantillaTarea entidad) {
        PlantillaTareaResponse res = new PlantillaTareaResponse();
        res.setIdPlantilla(entidad.getIdPlantilla());
        res.setNombre(entidad.getNombre());
        res.setDescripcion(entidad.getDescripcion());

        if (entidad.getCatalogoTarea() != null) {
            res.setIdCatalogoTarea(entidad.getCatalogoTarea().getIdCatalogoTarea());
            res.setNombreCatalogoTarea(entidad.getCatalogoTarea().getNombre());
        }
        return res;
    }
}