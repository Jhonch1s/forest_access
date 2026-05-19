package com.example.forest_access.api.controllers.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionTratamientoResponse {
    private Long idAsignacion;
    private Long idParcela;
    private String nombreParcela;
    private Long idRodal;
    private String nombreRodal;
    private Long idCampo;
    private String nombreCampo;
    private Long idTratamiento;
    private String nombreTratamiento;
    private String fechaAsignacion;
    private String fechaInicioEstimada;
    private String fechaFinEstimada;
    private String observaciones;
    private String estado;
}