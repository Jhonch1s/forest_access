package com.example.forest_access.dto;

import lombok.Data;

@Data
public class TratamientoDependenciaDTO {
    private TratamientoDTO tratamientoAnterior;
    private TratamientoDTO tratamientoPosterior;
    private Integer diasEsperaMinimo;
}