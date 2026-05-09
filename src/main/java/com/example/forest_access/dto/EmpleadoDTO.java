package com.example.forest_access.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Integer idEmpleado;
    private String nombre;
    private String cedula;
    private String telefono;
    private String email;
    private LocalDate fechaIngreso;
    private Boolean activo;
    private CategoriaEmpleadoDTO categoria; // Aquí sí usamos el DTO de la categoría
}