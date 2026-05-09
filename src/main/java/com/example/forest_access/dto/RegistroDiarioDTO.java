package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RegistroDiarioDTO {
    private Integer idRegistro;
    private EmpleadoDTO empleado;
    private LocalDate fecha;
    private BigDecimal jornales;
    private BigDecimal adelanto;
    private String observaciones;
}