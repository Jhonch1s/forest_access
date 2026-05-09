package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.RegistroDiarioRequest;
import com.example.forest_access.api.controllers.response.RegistroDiarioResponse;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.RegistroDiario;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.RegistroDiarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegistroDiarioService {

    private final RegistroDiarioRepository repository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<RegistroDiarioResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistroDiarioResponse findById(Integer id) {
        RegistroDiario registro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado con ID: " + id));
        return mapToResponse(registro);
    }

    @Transactional
    public RegistroDiarioResponse create(RegistroDiarioRequest request) {
        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (repository.existsByEmpleadoAndFecha(empleado, request.getFecha())) {
            throw new IllegalArgumentException(
                    String.format("El empleado %s ya tiene un registro para la fecha %s",
                            empleado.getNombre(), request.getFecha())
            );
        }

        RegistroDiario nuevo = new RegistroDiario();
        updateEntityFromRequest(nuevo, request, empleado);

        return mapToResponse(repository.save(nuevo));
    }

    @Transactional
    public RegistroDiarioResponse update(Integer id, RegistroDiarioRequest request) {
        RegistroDiario existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado"));

        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // Validar duplicado si cambia fecha o empleado
        if (!existente.getFecha().equals(request.getFecha()) ||
                !existente.getEmpleado().getIdEmpleado().equals(request.getIdEmpleado())) {
            if (repository.existsByEmpleadoAndFecha(empleado, request.getFecha())) {
                throw new IllegalArgumentException("Ya existe un registro para ese empleado en esa fecha.");
            }
        }

        updateEntityFromRequest(existente, request, empleado);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No encontrado");
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RegistroDiarioResponse> findPorIdEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- MAPPERS ---

    private void updateEntityFromRequest(RegistroDiario entidad, RegistroDiarioRequest request, Empleado empleado) {
        entidad.setFecha(request.getFecha());
        entidad.setEmpleado(empleado);
        entidad.setJornales(request.getJornales());
        entidad.setAdelanto(request.getAdelanto());
        entidad.setObservaciones(request.getObservaciones());
    }

    private RegistroDiarioResponse mapToResponse(RegistroDiario entidad) {
        RegistroDiarioResponse res = new RegistroDiarioResponse();
        res.setIdRegistro(entidad.getIdRegistro());
        res.setFecha(entidad.getFecha());
        res.setIdEmpleado(entidad.getEmpleado().getIdEmpleado());
        res.setNombreEmpleado(entidad.getEmpleado().getNombre());
        res.setJornales(entidad.getJornales());
        res.setAdelanto(entidad.getAdelanto());
        res.setObservaciones(entidad.getObservaciones());
        return res;
    }
}