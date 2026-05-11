package com.example.forest_access.api.controllers.response;

import com.example.forest_access.biz.dao.entities.Empleado;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HistoricoTratamientoResponse {
    private String nombreParcela;
    private String nombreTratamiento;
    private String nombreCuadrilla;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;
}
