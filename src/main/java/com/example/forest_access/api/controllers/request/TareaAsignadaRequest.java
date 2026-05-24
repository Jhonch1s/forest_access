package com.example.forest_access.api.controllers.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TareaAsignadaRequest {
    private Long idAsignacion;
    private Integer idCuadrilla;
    private Integer idCatalogoTarea;
    private String descripcion;
    private LocalDate fechaLimite;
}
