package com.example.forest_access.api.controllers.response;

import lombok.Data;

@Data
public class CuadrillaResponse {
    private Integer idCuadrilla;
    private String nombre;
    private Boolean activa;
}