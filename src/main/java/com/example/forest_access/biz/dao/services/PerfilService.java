package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.PerfilRequest;
import com.example.forest_access.api.controllers.response.PerfilResponse;
import com.example.forest_access.biz.dao.entities.Perfil;
import com.example.forest_access.biz.dao.repositories.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PerfilService {

    private final PerfilRepository repository;

    @Transactional(readOnly = true)
    public List<PerfilResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PerfilResponse findById(Long id) {
        Perfil perfil = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con id: " + id));
        return mapToResponse(perfil);
    }

    @Transactional
    public PerfilResponse create(PerfilRequest request) {
        // Validar unicidad
        if (repository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("El perfil con nombre '" + request.getNombre() + "' ya existe.");
        }

        Perfil nuevo = new Perfil();
        nuevo.setNombre(request.getNombre());

        return mapToResponse(repository.save(nuevo));
    }

    @Transactional
    public PerfilResponse update(Long id, PerfilRequest request) {
        Perfil existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado"));

        // Validar si el nombre cambió y si el nuevo ya existe
        if (!existente.getNombre().equalsIgnoreCase(request.getNombre())) {
            if (repository.findByNombre(request.getNombre()).isPresent()) {
                throw new IllegalArgumentException("Ya existe el perfil '" + request.getNombre() + "'");
            }
        }

        existente.setNombre(request.getNombre());
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Perfil no encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PerfilResponse findByNombre(String nombre) {
        Perfil perfil = repository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado: " + nombre));
        return mapToResponse(perfil);
    }

    // Mapper privado: Entidad -> Response
    private PerfilResponse mapToResponse(Perfil entidad) {
        PerfilResponse res = new PerfilResponse();
        res.setId(entidad.getId());
        res.setNombre(entidad.getNombre());
        return res;
    }
}