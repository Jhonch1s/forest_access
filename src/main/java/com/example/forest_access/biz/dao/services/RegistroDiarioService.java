package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.RegistroDiario;
import com.example.forest_access.biz.dao.repositories.RegistroDiarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistroDiarioService {

    private final RegistroDiarioRepository repository;

    @Transactional(readOnly = true)
    public List<RegistroDiario> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public RegistroDiario findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro diario no encontrado con ID: " + id));
    }

    @Transactional
    public RegistroDiario create(RegistroDiario registro) {
        // Validación: Un empleado solo puede tener un registro por día
        if (repository.existsByEmpleadoAndFecha(registro.getEmpleado(), registro.getFecha())) {
            throw new IllegalArgumentException(
                    String.format("El empleado %s ya tiene un registro para la fecha %s",
                            registro.getEmpleado().getNombre(), registro.getFecha())
            );
        }
        return repository.save(registro);
    }

    @Transactional
    public RegistroDiario update(Integer id, RegistroDiario datos) {
        RegistroDiario existente = findById(id);

        // Validar duplicado si se intenta cambiar la fecha o el empleado
        if (!existente.getFecha().equals(datos.getFecha()) ||
                !existente.getEmpleado().getIdEmpleado().equals(datos.getEmpleado().getIdEmpleado())) {

            if (repository.existsByEmpleadoAndFecha(datos.getEmpleado(), datos.getFecha())) {
                throw new IllegalArgumentException("Ya existe un registro para ese empleado en la nueva fecha seleccionada.");
            }
        }

        existente.setFecha(datos.getFecha());
        existente.setEmpleado(datos.getEmpleado());
        existente.setJornales(datos.getJornales());
        existente.setAdelanto(datos.getAdelanto());
        existente.setObservaciones(datos.getObservaciones());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        RegistroDiario existente = findById(id);
        repository.delete(existente);
    }

    @Transactional(readOnly = true)
    public List<RegistroDiario> findByFecha(LocalDate fecha) {
        return repository.findByFecha(fecha);
    }

    @Transactional(readOnly = true)
    public List<RegistroDiario> findPorEmpleadoYPeriodo(Empleado empleado, LocalDate inicio, LocalDate fin) {
        return repository.findByEmpleadoAndFechaBetween(empleado, inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<RegistroDiario> findPorIdEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado);
    }
}