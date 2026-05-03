package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository repository;

    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Empleado findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }

    @Transactional
    public Empleado create(Empleado empleado) {
        // Validar unicidad de Cédula
        if (repository.findByCedula(empleado.getCedula()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un empleado con la cédula: " + empleado.getCedula());
        }
        // Validar unicidad de Email
        if (repository.findByEmail(empleado.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un empleado con el email: " + empleado.getEmail());
        }
        return repository.save(empleado);
    }

    @Transactional
    public Empleado update(Integer id, Empleado datos) {
        Empleado existente = findById(id);

        // Validar cédula si cambió
        if (!existente.getCedula().equals(datos.getCedula())) {
            if (repository.findByCedula(datos.getCedula()).isPresent()) {
                throw new IllegalArgumentException("La nueva cédula ya está registrada.");
            }
        }

        // Actualización de campos
        existente.setNombre(datos.getNombre());
        existente.setCedula(datos.getCedula());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setFechaIngreso(datos.getFechaIngreso());
        existente.setActivo(datos.getActivo());
        existente.setCategoria(datos.getCategoria());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        Empleado existente = findById(id);
        repository.delete(existente);
    }

    // Consultas adicionales del repositorio
    @Transactional(readOnly = true)
    public List<Empleado> findByActivo(Boolean activo) {
        return repository.findByActivo(activo);
    }

    @Transactional(readOnly = true)
    public List<Empleado> findByCategoria(CategoriaEmpleado categoria) {
        return repository.findByCategoria(categoria);
    }
}