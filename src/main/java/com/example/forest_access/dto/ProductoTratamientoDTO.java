package com.example.forest_access.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoTratamientoDTO {
    private Integer idProducto;
    private Integer idTratamiento;
    private BigDecimal dosis;
    private String unidad;
}
