package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LiquidacionDTO {
    private Integer idLiquidacion;
    private EmpleadoDTO empleado;
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