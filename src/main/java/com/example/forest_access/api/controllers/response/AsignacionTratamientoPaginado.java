package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.util.List;

@Data
public class AsignacionTratamientoPaginado {
    private List<AsignacionTratamientoResponse> asignaciones;
    private Integer total;
    private Integer pagina;
    private Integer limite;
}
