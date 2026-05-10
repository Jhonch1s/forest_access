package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.EmpleadoResponse;
import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.CategoriaEmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.dto.EmpleadoDTO;
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

    // AHORA USA EL DTO GENERAL
    @Transactional
    public EmpleadoResponse create(EmpleadoDTO dto) {
        if (repository.findByCedula(dto.getCedula()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un empleado con la cédula: " + dto.getCedula());
        }

        Empleado nuevo = new Empleado();
        updateEntityFromDTO(nuevo, dto);

        return mapToResponse(repository.save(nuevo));
    }

    // AHORA USA EL DTO GENERAL
    @Transactional
    public EmpleadoResponse update(Integer id, EmpleadoDTO dto) {
        Empleado existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (!existente.getCedula().equals(dto.getCedula())) {
            if (repository.findByCedula(dto.getCedula()).isPresent()) {
                throw new IllegalArgumentException("La nueva cédula ya está registrada.");
            }
        }

        updateEntityFromDTO(existente, dto);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Empleado no encontrado");
        }
        repository.deleteById(id);
    }

    // MAPPER: De DTO a Entidad
    private void updateEntityFromDTO(Empleado entidad, EmpleadoDTO dto) {
        entidad.setNombre(dto.getNombre());
        entidad.setCedula(dto.getCedula());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());
        entidad.setFechaIngreso(dto.getFechaIngreso());
        entidad.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        // Como el DTO tiene un CategoriaEmpleadoDTO anidado, sacamos el ID de ahí
        if (dto.getCategoria() != null && dto.getCategoria().getIdCategoria() != null) {
            CategoriaEmpleado cat = categoriaRepository.findById(dto.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            entidad.setCategoria(cat);
        } else {
            entidad.setCategoria(null);
        }
    }

    // MAPPER: De Entidad a Response
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