package com.example.forest_access.api.controllers.request;

import lombok.Data;

@Data
public class TratamientoDependenciaRequest {
    private Integer idTratamientoAnterior;
    private Integer idTratamientoPosterior;
    private Integer diasEsperaMinimo;
}