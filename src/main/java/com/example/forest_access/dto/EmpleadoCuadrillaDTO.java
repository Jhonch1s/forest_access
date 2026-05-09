package com.example.forest_access.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoCuadrillaDTO {
    private EmpleadoDTO empleado;
    private CuadrillaDTO cuadrilla;
    private LocalDate fechaFin;
    private String rol;
}