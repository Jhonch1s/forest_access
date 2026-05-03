package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import com.example.forest_access.biz.dao.repositories.EmpleadoCuadrillaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmpleadoCuadrillaService {

    private final EmpleadoCuadrillaRepository repository;

    @Transactional
    public List<EmpleadoCuadrilla> findAll() {
        return repository.findAll();
    }

    @Transactional
    public EmpleadoCuadrilla findById(EmpleadoCuadrillaId id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación Empleado-Cuadrilla no encontrada"));
    }

    @Transactional
    public EmpleadoCuadrilla create(EmpleadoCuadrilla relacion) {
        // Al usar @EmbeddedId, es vital que el ID esté seteado antes del save
        if (relacion.getId() == null) {
            relacion.setId(new EmpleadoCuadrillaId(
                    relacion.getCuadrilla().getIdCuadrilla(),
                    relacion.getEmpleado().getIdEmpleado(),
                    LocalDate.now()
            ));
        }

        if (repository.existsById(relacion.getId())) {
            throw new IllegalArgumentException("El empleado ya está asignado a esa cuadrilla");
        }
        return repository.save(relacion);
    }

    @Transactional
    public void delete(EmpleadoCuadrillaId id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No existe la asignación a eliminar");
        }
        repository.deleteById(id);
    }

    // Consultas específicas del repositorio
    @Transactional(readOnly = true)
    public List<EmpleadoCuadrilla> findByCuadrilla(Integer idCuadrilla) {
        return repository.findByCuadrilla_IdCuadrilla(idCuadrilla);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoCuadrilla> findActivosPorCuadrilla(Cuadrilla cuadrilla) {
        return repository.findByCuadrillaAndFechaFinIsNull(cuadrilla);
    }
}