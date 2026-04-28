package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmpleadoHabilitacionRepository
        extends JpaRepository<EmpleadoHabilitacion, EmpleadoHabilitacionId> {

    List<EmpleadoHabilitacion> findByEmpleado(Empleado empleado);
    List<EmpleadoHabilitacion> findByEmpleado_IdEmpleado(Integer idEmpleado);
    List<EmpleadoHabilitacion> findByHabilitacion(Habilitacion habilitacion);

    // útil para alertas de vencimiento
    List<EmpleadoHabilitacion> findByFechaVencimientoBefore(LocalDate fecha);
    List<EmpleadoHabilitacion> findByFechaVencimientoBetween(LocalDate desde, LocalDate hasta);
}
