package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParcelaResponse {
    private String nombre;
    private BigDecimal area;
    private String tipoCultivo;
    private Integer anioPlantacion;
    private BigDecimal coordLat;
    private BigDecimal coordLng;
    private String nombreRodal;
}
