package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TareaDTO {
    private Integer idTarea;
    private CatalogoTareaDTO catalogoTarea;
    private EstadoDTO estado;
    private EmpleadoDTO empleado;
    private HistoricoTratamientoDTO historicoTratamiento;
    private PlantillaTareaDTO plantilla;
    private LocalDate fechaCreacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinalizacion;
    private String descripcion;
    private BigDecimal horas;
    private String observaciones;
}