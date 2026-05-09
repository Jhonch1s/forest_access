package com.example.forest_access.dto;

import lombok.Data;

@Data
public class CatalogoTareaDTO {
    private Integer idCatalogoTarea;
    private String nombre;
    private String descripcion;
    private HabilitacionDTO requiereHabilitacion;
}