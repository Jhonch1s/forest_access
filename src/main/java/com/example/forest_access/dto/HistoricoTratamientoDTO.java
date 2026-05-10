package com.example.forest_access.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoricoTratamientoDTO {
    private Integer idHistorico;
    private Integer idParcela;
    private Integer idTratamiento;
    private Integer cuadrilla;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;
}
