package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoRequest {
    private Integer idEmpleado;
    private String rol;
}