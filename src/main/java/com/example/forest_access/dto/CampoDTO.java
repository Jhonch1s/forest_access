package com.example.forest_access.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CampoDTO {
    private String nombre;
    private String padron;
    private BigDecimal superficieTotal;
    private BigDecimal coordLat;
    private BigDecimal coordLng;
}
