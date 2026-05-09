package com.example.forest_access.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RodalDTO {
    private String nombre;
    private BigDecimal area;
    private BigDecimal coordLat;
    private BigDecimal coordLng;
    private Integer idCampo;
}
