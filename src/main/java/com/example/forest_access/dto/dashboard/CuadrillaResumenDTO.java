package com.example.forest_access.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuadrillaResumenDTO {
    private Integer id;
    private String nombre;
    private String tratamiento;
    private String fecha;
}
