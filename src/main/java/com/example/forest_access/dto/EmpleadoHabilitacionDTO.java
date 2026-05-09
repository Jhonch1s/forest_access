package com.example.forest_access.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoHabilitacionDTO {
    private Integer idEmpleado;
    private Integer idHabilitacion;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
}
