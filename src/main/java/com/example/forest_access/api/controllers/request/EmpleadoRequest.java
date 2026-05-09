package com.example.forest_access.api.controllers.request;

import lombok.Data; // <--- ¡Importante!
import java.time.LocalDate;

@Data
public class EmpleadoRequest {
    private String nombre;
    private String cedula;
    private String telefono;
    private String email;
    private LocalDate fechaIngreso;
    private Boolean activo;
    private Integer idCategoria;
}