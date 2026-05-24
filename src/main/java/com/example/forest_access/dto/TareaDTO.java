package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TareaDTO {
    private Integer idTarea;
    private Integer idAsignacion;
    private CatalogoTareaDTO catalogoTarea;
    private EstadoDTO estado;
    private EmpleadoDTO empleado;
    private LocalDate fecha;
    private String descripcion;
    private BigDecimal horas;
    private String observaciones;
}
