package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CuadrillaService {

    private final CuadrillaRepository repository;

    public List<Cuadrilla> findAll(){
        return repository.findAll();
    }

    public Cuadrilla findById(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cuadrilla no encontrada con Id: " + id
                ));
    }

    public List<Cuadrilla> findActivas() {
        return repository.findByActiva(true);
    }

    @Transactional
    public Cuadrilla create(Cuadrilla cuadrilla){
        if (repository.findByNombre(cuadrilla.getNombre()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe una cuadrilla con el nombre: " + cuadrilla.getNombre()
            );
        }
        return repository.save(cuadrilla);
    }


    @Transactional
    public Cuadrilla update(Integer id, Cuadrilla datos) {
        Cuadrilla existente = findById(id);

        // Validar duplicado solo si el nombre cambió
        if (!existente.getNombre().equalsIgnoreCase(datos.getNombre())) {
            if (repository.findByNombre(datos.getNombre()).isPresent()) {
                throw new IllegalArgumentException(
                        "Error: El nombre '" + datos.getNombre() + "' ya está en uso por otra cuadrilla."
                );
            }
        }

        // Actualizamos los campos el modelo
        existente.setNombre(datos.getNombre());
        existente.setActiva(datos.getActiva());

        // Al usar @Transactional y haber buscado la entidad primero,
        // save() realizará un UPDATE correcto sin conflictos de concurrencia.
        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        Cuadrilla existente = findById(id);
        repository.delete(existente);
    }
}
