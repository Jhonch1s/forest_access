package com.example.forest_access.api.controllers.response; // o la carpeta que uses

import lombok.Data;

import java.time.LocalDate;

@Data // <--- Sin esto, el Service no puede usar los 'set'
public class EmpleadoResponse {
    private Integer idEmpleado;
    private String nombre;
    private String cedula;
    private String telefono;
    private String email;
    private LocalDate fechaIngreso;
    private Boolean activo;
    private Integer idCategoria;
    private String nombreCategoria;
}