package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.AsignacionTratamiento;
import com.example.forest_access.enums.EstadoAsignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignacionTratamientoRepository
        extends JpaRepository<AsignacionTratamiento, Long> {

    List<AsignacionTratamiento> findByParcelaIdParcela(Long idParcela);

    List<AsignacionTratamiento> findByParcelaRodalIdRodal(Long idRodal);

    List<AsignacionTratamiento> findByTratamientoIdTratamiento(Long idTratamiento);

    List<AsignacionTratamiento> findByEstado(EstadoAsignacion estado);

    boolean existsByParcelaIdParcelaAndTratamientoIdTratamientoAndEstadoNot(
            Long idParcela, Long idTratamiento, EstadoAsignacion estado);

    Optional<AsignacionTratamiento> findTopByParcelaIdParcelaAndTratamientoIdTratamientoOrderByFechaFinEstimadaDesc(
            Integer idParcela, Integer idTratamiento);

    Optional<AsignacionTratamiento> findTopByParcelaIdParcelaAndTratamientoIdTratamientoAndEstadoOrderByFechaFinEstimadaDesc(
            Integer idParcela, Integer idTratamiento, EstadoAsignacion estado);
}
