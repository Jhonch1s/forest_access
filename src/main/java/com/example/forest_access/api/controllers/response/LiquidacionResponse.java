package com.example.forest_access.api.controllers.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LiquidacionResponse {
    private Integer idLiquidacion;
    private String nombreEmpleado;
    private String cedulaEmpleado;
    private String periodo;
    private BigDecimal totalFinal;
    private String observaciones;
}