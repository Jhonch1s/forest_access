package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoHabilitacionResponse {
    private Integer idEmpleado;
    private Integer idHabilitacion;
    private String nombreEmpleado;
    private String nombreHabilitacion;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
}
