package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpleadoHabilitacionRepository
        extends JpaRepository<EmpleadoHabilitacion, EmpleadoHabilitacionId> {

    List<EmpleadoHabilitacion> findByEmpleado(Empleado empleado);
    List<EmpleadoHabilitacion> findByEmpleado_IdEmpleado(Integer idEmpleado);
    List<EmpleadoHabilitacion> findByHabilitacion(Habilitacion habilitacion);

    List<EmpleadoHabilitacion> findByFechaVencimientoBefore(LocalDate fecha);
    List<EmpleadoHabilitacion> findByFechaVencimientoBetween(LocalDate desde, LocalDate hasta);

    Optional<EmpleadoHabilitacion> findById(EmpleadoHabilitacionId id);

    @Override
    @EntityGraph(attributePaths = {"empleado", "habilitacion"})
    List<EmpleadoHabilitacion> findAll();
}
