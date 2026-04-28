package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LiquidacionRepository extends JpaRepository<Liquidacion, Integer> {

    List<Liquidacion> findByEmpleado(Empleado empleado);
    List<Liquidacion> findByEmpleado_IdEmpleado(Integer idEmpleado);

    // liquidaciones de un período, útil para cierre mensual
    List<Liquidacion> findByPeriodoInicioBetween(LocalDate desde, LocalDate hasta);

    // verificar que no exista ya una liquidación para ese empleado en ese período
    boolean existsByEmpleadoAndPeriodoInicioAndPeriodoFin(
            Empleado empleado, LocalDate periodoInicio, LocalDate periodoFin
    );

    // historial completo de un empleado ordenado por fecha
    List<Liquidacion> findByEmpleadoOrderByPeriodoInicioDesc(Empleado empleado);
}
