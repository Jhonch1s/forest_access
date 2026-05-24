package com.example.forest_access.api.controllers.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TareaResponse {
    private Integer idTarea;
    private Integer idAsignacion;
    private String descripcion;
    private BigDecimal horas;
    private LocalDate fecha;
    private String observaciones;

    private Integer idEmpleado;
    private String nombreEmpleado;
    private Integer idEstado;
    private String nombreEstado;
    private Integer idCatalogoTarea;
    private String nombreTareaCatalogo;
}
