package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Perfil;
import com.example.forest_access.biz.dao.repositories.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PerfilService {

    private final PerfilRepository repository;

    @Transactional(readOnly = true)
    public List<Perfil> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Perfil findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con id: " + id));
    }

    @Transactional
    public Perfil create(Perfil perfil) {
        // Validar que el nombre del perfil no exista ya
        if (repository.findByNombre(perfil.getNombre()).isPresent()) {
            throw new IllegalArgumentException("El perfil con nombre '" + perfil.getNombre() + "' ya existe.");
        }
        return repository.save(perfil);
    }

    @Transactional
    public Perfil update(Long id, Perfil datos) {
        Perfil existente = findById(id);

        // Si el nombre cambió, validar que el nuevo nombre no esté en uso
        if (!existente.getNombre().equalsIgnoreCase(datos.getNombre())) {
            if (repository.findByNombre(datos.getNombre()).isPresent()) {
                throw new IllegalArgumentException("No se puede actualizar: ya existe el perfil '" + datos.getNombre() + "'");
            }
        }

        existente.setNombre(datos.getNombre());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        // Antes de borrar podriamos verificar si hay usuarios asociados para evitar errores de integridad
        Perfil existente = findById(id);
        repository.delete(existente);
    }

    @Transactional
    public Perfil findByNombre(String nombre) {
        return repository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con nombre: " + nombre));
    }
}