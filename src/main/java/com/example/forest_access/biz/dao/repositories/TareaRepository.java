package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findByEmpleado(Empleado empleado);
    List<Tarea> findByEmpleado_IdEmpleado(Integer idEmpleado);

    List<Tarea> findByEstado(Estado estado);
    List<Tarea> findByEstado_Nombre(String nombreEstado);

    List<Tarea> findByHistoricoTratamiento(HistoricoTratamiento historico);
    List<Tarea> findByHistoricoTratamiento_IdHistorico(Integer idHistorico);

    // tareas de un empleado en un período, útil para liquidación
    List<Tarea> findByEmpleadoAndFechaFinalizacionBetween(
            Empleado empleado, LocalDate desde, LocalDate hasta
    );

    // tareas pendientes o en curso de un empleado
    List<Tarea> findByEmpleadoAndEstado_NombreIn(
            Empleado empleado, List<String> estados
    );

    // tareas de una parcela a través del histórico
    List<Tarea> findByHistoricoTratamiento_Parcela_IdParcela(Integer idParcela);
}
