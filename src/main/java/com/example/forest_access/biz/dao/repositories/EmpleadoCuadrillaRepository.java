package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.EmpleadoCuadrilla;
import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoCuadrillaRepository
        extends JpaRepository<EmpleadoCuadrilla, EmpleadoCuadrillaId> {

    List<EmpleadoCuadrilla> findByCuadrilla(Cuadrilla cuadrilla);
    List<EmpleadoCuadrilla> findByCuadrilla_IdCuadrilla(Integer idCuadrilla);
    List<EmpleadoCuadrilla> findByEmpleado(Empleado empleado);

    // miembros activos de una cuadrilla (fecha_fin IS NULL)
    List<EmpleadoCuadrilla> findByCuadrillaAndFechaFinIsNull(Cuadrilla cuadrilla);

    // cuadrillas actuales de un empleado
    List<EmpleadoCuadrilla> findByEmpleadoAndFechaFinIsNull(Empleado empleado);
}
