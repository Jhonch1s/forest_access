package com.example.forest_access.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParcelaDTO {
    private String nombre;
    private BigDecimal area;
    private String tipoCultivo;
    private Integer anioPlantacion;
    private BigDecimal coordLat;
    private BigDecimal coordLng;
    private Integer idRodal;
}
