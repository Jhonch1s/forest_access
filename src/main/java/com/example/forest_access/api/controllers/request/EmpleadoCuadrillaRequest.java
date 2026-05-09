package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoCuadrillaRequest {
    private Integer idEmpleado;
    private Integer idCuadrilla;
    private LocalDate fechaInicio; // Suele ser necesaria aunque no esté explícita en la entidad (o se toma hoy)
    private LocalDate fechaFin;
    private String rol;
}

