package com.example.forest_access.biz.dao.entities.embeddables;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class EmpleadoCuadrillaId implements Serializable {

    @Column(name = "id_cuadrilla")
    private Integer idCuadrilla;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    public EmpleadoCuadrillaId() {}

    public EmpleadoCuadrillaId(Integer idCuadrilla, Integer idEmpleado, LocalDate fechaInicio) {
        this.idCuadrilla = idCuadrilla;
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpleadoCuadrillaId)) return false;
        EmpleadoCuadrillaId that = (EmpleadoCuadrillaId) o;
        return Objects.equals(idCuadrilla, that.idCuadrilla) &&
                Objects.equals(idEmpleado, that.idEmpleado) &&
                Objects.equals(fechaInicio, that.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuadrilla, idEmpleado, fechaInicio);
    }

    // getters y setters
}