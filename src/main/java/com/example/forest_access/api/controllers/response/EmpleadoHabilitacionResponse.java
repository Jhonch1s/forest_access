package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoHabilitacionResponse {
    private String nombreEmpleado;
    private String nombreHabilitacion;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
}
