package com.example.forest_access.api.controllers.response;

import lombok.Data;

@Data
public class CatalogoTareaResponse {
    private Integer idCatalogoTarea;
    private String nombre;
    private String descripcion;
    private Integer idHabilitacion;
    private String nombreHabilitacion;
}