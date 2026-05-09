package com.example.forest_access.api.controllers.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoCuadrillaResponse {
    private Integer idEmpleado;
    private String nombreEmpleado;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private String rol;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean esActivo;
}