package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ReporteEmpleadoDTO {
    private Integer idEmpleado;
    private String nombre;
    private String cedula;
    private String nombreCategoria;
    private BigDecimal valorJornal;
    private BigDecimal totalHoras;
    private int totalTareas;
    private int diasTrabajados;
    private List<ReporteTareaDTO> tareas;
    private List<ReporteHabilitacionDTO> habilitaciones;
}
