package com.example.forest_access.api.controllers.response;

import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import lombok.Data;

import java.util.List;

@Data
public class PaginadoEmpleadoHabilitacion {
    List<EmpleadoHabilitacionResponse> empleadoHabilitacions;
    private Integer total;
    private Integer pagina;
    private Integer limite;
}
