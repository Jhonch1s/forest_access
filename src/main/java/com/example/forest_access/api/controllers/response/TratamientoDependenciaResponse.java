package com.example.forest_access.api.controllers.response;

import lombok.Data;

@Data
public class TratamientoDependenciaResponse {
    private Integer idTratamientoAnterior;
    private String nombreTratamientoAnterior;
    private Integer idTratamientoPosterior;
    private String nombreTratamientoPosterior;
    private Integer diasEsperaMinimo;
}