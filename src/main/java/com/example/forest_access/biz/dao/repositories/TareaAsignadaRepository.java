package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.TareaAsignada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaAsignadaRepository extends JpaRepository<TareaAsignada, Integer> {

    List<TareaAsignada> findByCuadrilla_IdCuadrilla(Integer idCuadrilla);

    List<TareaAsignada> findByCuadrilla_IdCuadrillaAndFechaLimiteGreaterThanEqual(
            Integer idCuadrilla, LocalDate fecha);

    List<TareaAsignada> findByAsignacionTratamiento_IdAsignacionAndCuadrilla_IdCuadrilla(
            Long idAsignacion, Integer idCuadrilla);

    List<TareaAsignada> findByAsignacionTratamiento_IdAsignacionAndCuadrilla_IdCuadrillaAndFechaLimiteGreaterThanEqual(
            Long idAsignacion, Integer idCuadrilla, LocalDate fecha);
}
