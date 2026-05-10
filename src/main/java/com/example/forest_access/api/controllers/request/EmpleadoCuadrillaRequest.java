package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoCuadrillaRequest {
    private Integer idEmpleado;
    private Integer idCuadrilla;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String rol;
}

