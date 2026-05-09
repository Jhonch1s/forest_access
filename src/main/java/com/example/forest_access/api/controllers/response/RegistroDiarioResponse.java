package com.example.forest_access.api.controllers.response;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class RegistroDiarioResponse {
    private Integer idRegistro;
    private LocalDate fecha;

    private Integer idEmpleado;
    private String nombreEmpleado;

    private BigDecimal jornales;
    private BigDecimal adelanto;
    private String observaciones;
}