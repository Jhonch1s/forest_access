package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.EmpleadoRequest;
import com.example.forest_access.api.controllers.response.EmpleadoResponse;
import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.CategoriaEmpleadoRepository; // Necesario
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository repository;
    private final CategoriaEmpleadoRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<EmpleadoResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse findById(Integer id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
        return mapToResponse(empleado);
    }

    @Transactional
    public EmpleadoResponse create(EmpleadoRequest request) {
        // Validaciones de unicidad
        if (repository.findByCedula(request.getCedula()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un empleado con la cédula: " + request.getCedula());
        }

        // Transformar Request -> Entidad
        Empleado nuevo = new Empleado();
        updateEntityFromRequest(nuevo, request);

        return mapToResponse(repository.save(nuevo));
    }

    @Transactional
    public EmpleadoResponse update(Integer id, EmpleadoRequest request) {
        Empleado existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // Validar cédula si cambió
        if (!existente.getCedula().equals(request.getCedula())) {
            if (repository.findByCedula(request.getCedula()).isPresent()) {
                throw new IllegalArgumentException("La nueva cédula ya está registrada.");
            }
        }

        updateEntityFromRequest(existente, request);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Empleado no encontrado");
        }
        repository.deleteById(id);
    }

    // Métodos privados de mapeo (Traducción)
    private void updateEntityFromRequest(Empleado entidad, EmpleadoRequest request) {
        entidad.setNombre(request.getNombre());
        entidad.setCedula(request.getCedula());
        entidad.setTelefono(request.getTelefono());
        entidad.setEmail(request.getEmail());
        entidad.setFechaIngreso(request.getFechaIngreso());
        entidad.setActivo(request.getActivo() != null ? request.getActivo() : true);

        if (request.getIdCategoria() != null) {
            CategoriaEmpleado cat = categoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            entidad.setCategoria(cat);
        }
    }

    private EmpleadoResponse mapToResponse(Empleado entidad) {
        EmpleadoResponse res = new EmpleadoResponse();
        res.setIdEmpleado(entidad.getIdEmpleado());
        res.setNombre(entidad.getNombre());
        res.setCedula(entidad.getCedula());
        res.setTelefono(entidad.getTelefono());
        res.setEmail(entidad.getEmail());
        res.setFechaIngreso(entidad.getFechaIngreso());
        res.setActivo(entidad.getActivo());

        if (entidad.getCategoria() != null) {
            res.setIdCategoria(entidad.getCategoria().getIdCategoria());
            res.setNombreCategoria(entidad.getCategoria().getNombre());
        }
        return res;
    }
}