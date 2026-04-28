package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoricoTratamientoRepository
        extends JpaRepository<HistoricoTratamiento, Integer> {

    List<HistoricoTratamiento> findByParcela(Parcela parcela);
    List<HistoricoTratamiento> findByParcela_IdParcela(Integer idParcela);

    // todos los tratamientos de una parcela ordenados por fecha, útil para validar dependencias
    List<HistoricoTratamiento> findByParcelaOrderByFechaInicioDesc(Parcela parcela);

    List<HistoricoTratamiento> findByCuadrilla(Cuadrilla cuadrilla);
    List<HistoricoTratamiento> findByCuadrilla_IdCuadrilla(Integer idCuadrilla);

    List<HistoricoTratamiento> findByTratamiento(Tratamiento tratamiento);

    // tratamientos activos (sin fecha fin)
    List<HistoricoTratamiento> findByCuadrillaAndFechaFinIsNull(Cuadrilla cuadrilla);

    // tratamientos en un rango de fechas sobre una parcela
    List<HistoricoTratamiento> findByParcelaAndFechaInicioBetween(
            Parcela parcela, LocalDate desde, LocalDate hasta
    );
}
