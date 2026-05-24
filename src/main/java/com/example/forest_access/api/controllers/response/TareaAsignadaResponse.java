package com.example.forest_access.api.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaAsignadaResponse {
    private Integer idTareaAsignada;
    private Long idAsignacion;
    private String nombreParcela;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private Integer idCatalogoTarea;
    private String nombreCatalogoTarea;
    private String descripcion;
    private LocalDate fechaLimite;
}
