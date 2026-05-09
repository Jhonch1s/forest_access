package com.example.forest_access.api.controllers.response;

import lombok.Data;

@Data
public class PlantillaTareaResponse {
    private Integer idPlantilla;
    private String nombre;
    private String descripcion;
    private Integer idCatalogoTarea;
    private String nombreCatalogoTarea; // Ejemplo: "Poda", "Riego", etc.
}