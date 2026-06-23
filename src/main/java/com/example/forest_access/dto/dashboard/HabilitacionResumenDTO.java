package com.example.forest_access.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabilitacionResumenDTO {
    private Integer id;
    private String empleado;
    private String trabajo;
    private String fecha;
    private String estado;
}
