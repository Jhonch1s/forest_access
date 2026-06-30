package com.example.forest_access.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuadrillaResumenDTO {
    private Integer id;
    private String nombre;
    private List<String> tratamientos;
    private String fecha;
}
