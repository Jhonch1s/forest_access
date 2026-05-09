package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class LiquidacionRequest {
    private Integer idEmpleado;
    private LocalDate periodoInicio;
    private LocalDate periodoFin;
    private BigDecimal totalJornales;
    private BigDecimal valorJornal;
    private BigDecimal totalNominal;
    private BigDecimal totalProduccion;
    private BigDecimal totalIncentivo;
    private BigDecimal adelantos;
    private BigDecimal totalFinal;
    private String observaciones;
}