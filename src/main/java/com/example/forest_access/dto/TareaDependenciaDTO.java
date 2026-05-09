package com.example.forest_access.dto;

import lombok.Data;

@Data
public class TareaDependenciaDTO {
    private Integer idTareaAnterior;
    private Integer idTareaPosterior;
    private Integer diasEsperaMinimo;

}
