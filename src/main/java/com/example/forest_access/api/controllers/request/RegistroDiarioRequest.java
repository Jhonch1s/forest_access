package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RegistroDiarioRequest {
    private Integer idEmpleado;
    private LocalDate fecha;
    private BigDecimal jornales;
    private BigDecimal adelanto;
    private String observaciones;
}