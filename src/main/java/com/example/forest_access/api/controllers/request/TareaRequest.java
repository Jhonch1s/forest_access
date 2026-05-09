package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class TareaRequest {
    private String descripcion;
    private String observaciones;
    private java.math.BigDecimal horas;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinalizacion;

    // IDs para las relaciones
    private Integer idEmpleado;
    private Integer idEstado;
    private Integer idCatalogoTarea;
    private Integer idPlantilla;
    private Integer idHistoricoTratamiento;
}