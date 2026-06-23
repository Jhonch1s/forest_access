package com.example.forest_access.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasDTO {
    private List<Integer> productividadSemanal; // Lunes a Viernes
    private List<BigDecimal> evolucionHoras; // Semana -3, Semana -2, Semana -1, Semana Actual
    private List<String> labelsSemanas; // Nombres de las semanas para el gráfico
    private Map<String, Integer> estadoTareas; // "En proceso", "Pendiente", "Finalizada"
}
