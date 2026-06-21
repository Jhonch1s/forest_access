package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TareaSimpleResponse {
    private Integer idTarea;
    private Integer idAsignacion;
    private String descripcion;
    private BigDecimal horas;
    private LocalDate fecha;
    private String observaciones;
    private String nombreEstado;
    private String nombreCuadrilla;

}
