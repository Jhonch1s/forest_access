package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.PlantillaTarea;
import com.example.forest_access.biz.dao.repositories.PlantillaTareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PlantillaTareaService {

    private final PlantillaTareaRepository repository;

    @Transactional(readOnly = true)
    public List<PlantillaTarea> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PlantillaTarea findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plantilla de tarea no encontrada con ID: " + id));
    }

    @Transactional
    public PlantillaTarea create(PlantillaTarea plantilla) {
        // Validación de nombre único para la plantilla
        if (repository.findByNombre(plantilla.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una plantilla con el nombre: " + plantilla.getNombre());
        }
        return repository.save(plantilla);
    }

    @Transactional
    public PlantillaTarea update(Integer id, PlantillaTarea datos) {
        PlantillaTarea existente = findById(id);

        // Validar nombre si cambió
        if (!existente.getNombre().equalsIgnoreCase(datos.getNombre())) {
            if (repository.findByNombre(datos.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + datos.getNombre() + "' ya está en uso.");
            }
        }

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setCatalogoTarea(datos.getCatalogoTarea());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        PlantillaTarea existente = findById(id);
        repository.delete(existente);
    }

    @Transactional
    public List<PlantillaTarea> findByCatalogo(Integer idCatalogo) {
        return repository.findByCatalogoTarea_IdCatalogoTarea(idCatalogo);
    }

    @Transactional
    public List<PlantillaTarea> findByCatalogo(CatalogoTarea catalogo) {
        return repository.findByCatalogoTarea(catalogo);
    }
}