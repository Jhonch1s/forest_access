package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Integer> {

    List<RegistroDiario> findByEmpleado(Empleado empleado);
    List<RegistroDiario> findByEmpleado_IdEmpleado(Integer idEmpleado);

    // registros de un empleado en un período, útil para calcular liquidación
    List<RegistroDiario> findByEmpleadoAndFechaBetween(
            Empleado empleado, LocalDate desde, LocalDate hasta
    );

    // todos los registros de un día, útil para control diario
    List<RegistroDiario> findByFecha(LocalDate fecha);

    // verificar si ya existe registro para ese empleado ese día
    Optional<RegistroDiario> findByEmpleadoAndFecha(Empleado empleado, LocalDate fecha);
    boolean existsByEmpleadoAndFecha(Empleado empleado, LocalDate fecha);
}