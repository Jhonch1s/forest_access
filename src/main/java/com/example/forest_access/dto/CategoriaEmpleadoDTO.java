package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategoriaEmpleadoDTO {
    private Integer idCategoria;
    private String nombre;
    private BigDecimal valorJornal;
    private String descripcion;
}