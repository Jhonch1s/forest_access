package com.example.forest_access.api.controllers.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoRequest {
    // Ejemplo de cómo lo usarías a futuro para un filtro de búsqueda:
    private String nombreParcial;
    private Boolean soloActivos;
    private LocalDate ingresoDesde;
    private LocalDate ingresoHasta;
}