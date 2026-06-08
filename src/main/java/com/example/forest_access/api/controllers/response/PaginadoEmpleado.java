package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginadoEmpleado {
    private List<EmpleadoResponse> empleados;
    private Integer total;
    private Integer pagina;
    private Integer limite;
}
