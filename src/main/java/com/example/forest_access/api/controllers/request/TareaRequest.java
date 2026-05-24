package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class TareaRequest {
    private String descripcion;
    private String observaciones;
    private BigDecimal horas;
    private LocalDate fecha;

    private Integer idAsignacion;
    private Integer idEmpleado;
    private Integer idEstado;
    private Integer idCatalogoTarea;
}
