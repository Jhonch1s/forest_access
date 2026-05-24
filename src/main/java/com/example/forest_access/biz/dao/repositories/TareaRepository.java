package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.AsignacionTratamiento;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findByEmpleado(Empleado empleado);
    List<Tarea> findByEmpleado_IdEmpleado(Integer idEmpleado);

    List<Tarea> findByEstado(Estado estado);
    List<Tarea> findByEstado_Nombre(String nombreEstado);

    List<Tarea> findByAsignacionTratamiento(AsignacionTratamiento asignacion);
    List<Tarea> findByAsignacionTratamiento_IdAsignacion(Long idAsignacion);

    List<Tarea> findByEmpleadoAndFechaBetween(Empleado empleado, LocalDate desde, LocalDate hasta);

    List<Tarea> findByEmpleadoAndEstado_NombreIn(Empleado empleado, List<String> estados);
}
