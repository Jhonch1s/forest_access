package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RodalResponse {
    private String nombre;
    private BigDecimal area;
    private BigDecimal coordLat;
    private BigDecimal coordLng;
    private String nombreCampo;
}
