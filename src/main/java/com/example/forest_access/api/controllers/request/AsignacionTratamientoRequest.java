package com.example.forest_access.api.controllers.request;

import com.example.forest_access.enums.EstadoAsignacion;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionTratamientoRequest {
    private Long idAsignacion;
    private Long idParcela;        // Para asignación individual
    private Long idRodal;          // Para asignación masiva por rodal
    private Long idTratamiento;
    private String fechaAsignacion;        // ISO date string
    private String fechaInicioEstimada;
    private String fechaFinEstimada;
    private String observaciones;
    private EstadoAsignacion estado;
}