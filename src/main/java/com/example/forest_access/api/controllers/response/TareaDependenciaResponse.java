package com.example.forest_access.api.controllers.response;

import lombok.Data;

@Data
public class TareaDependenciaResponse {
    private String tareaAnterior;
    private String tareaPosterior;
    private Integer diasEsperaMinimo;
}
