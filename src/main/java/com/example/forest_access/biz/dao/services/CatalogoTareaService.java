package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.CatalogoTareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoTareaService {

    private final CatalogoTareaRepository repository;

    public List<CatalogoTarea> findAll() {
        return repository.findAll();
    }

    public CatalogoTarea findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tarea no encontrada con ID: " + id
                ));
    }

    @Transactional
    public CatalogoTarea create(CatalogoTarea tarea) {

        if (repository.findByNombre(tarea.getNombre()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe una tarea con el nombre: " + tarea.getNombre()
            );
        }
        return repository.save(tarea);
    }

    @Transactional
    public CatalogoTarea update(Integer id, CatalogoTarea datos) {
        CatalogoTarea existente = findById(id);

        if (!existente.getNombre().equalsIgnoreCase(datos.getNombre())) {
            if (repository.findByNombre(datos.getNombre()).isPresent()) {
                throw new IllegalArgumentException(
                        "Error: El nombre '" + datos.getNombre() + "' ya está en uso por otra tarea."
                );
            }
        }

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setRequiereHabilitacion(datos.getRequiereHabilitacion());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        CatalogoTarea existente = findById(id);
        repository.delete(existente);
    }

    public List<CatalogoTarea> findSinHabilitacion() {
        return repository.findByRequiereHabilitacionIsNull();
    }

    public List<CatalogoTarea> findPorHabilitacion(Habilitacion h) {
        return repository.findByRequiereHabilitacion(h);
    }
}