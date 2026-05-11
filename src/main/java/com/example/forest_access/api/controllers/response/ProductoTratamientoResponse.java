package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoTratamientoResponse {
    private String nombreProducto;
    private String nombreTratamiento;
    private BigDecimal dosis;
    private String unidad;
}
