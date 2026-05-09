package com.example.forest_access.dto;

import lombok.Data;

@Data
public class PlantillaTareaDTO {
    private Integer idPlantilla;
    private String nombre;
    private String descripcion;
    private CatalogoTareaDTO catalogoTarea;
}