package com.example.forest_access.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private List<CuadrillaResumenDTO> cuadrillasActivas;
    private List<HabilitacionResumenDTO> habilitacionesPorVencer;
    private EstadisticasDTO estadisticas;
}
