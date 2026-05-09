package com.example.forest_access.api.controllers.request;

import lombok.Data;

@Data
public class PlantillaTareaRequest {
    private String nombre;
    private String descripcion;
    private Integer idCatalogoTarea;
}