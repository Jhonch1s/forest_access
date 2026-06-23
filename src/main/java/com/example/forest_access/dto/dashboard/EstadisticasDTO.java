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
    private List<Integer> productividadSemanal;
    private List<BigDecimal> evolucionHoras;
    private List<String> labelsSemanas;
    private Map<String, Integer> estadoTareas;
}
