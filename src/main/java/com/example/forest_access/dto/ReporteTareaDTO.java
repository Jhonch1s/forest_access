package com.example.forest_access.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReporteTareaDTO {
    private String nombreCatalogo;
    private int cantidad;
    private BigDecimal horas;
}
