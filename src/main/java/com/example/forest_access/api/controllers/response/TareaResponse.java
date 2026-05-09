package com.example.forest_access.api.controllers.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TareaResponse {
    private Integer idTarea;
    private String descripcion;
    private java.math.BigDecimal horas;
    private LocalDate fechaFinalizacion;

    // Nombres para mostrar en el Frontend
    private String nombreEmpleado;
    private String nombreEstado;
    private String nombreTareaCatalogo;
}