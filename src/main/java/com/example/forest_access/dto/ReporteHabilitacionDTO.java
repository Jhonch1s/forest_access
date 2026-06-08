package com.example.forest_access.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReporteHabilitacionDTO {
    private String nombreHabilitacion;
    private LocalDate fechaVencimiento;
}
