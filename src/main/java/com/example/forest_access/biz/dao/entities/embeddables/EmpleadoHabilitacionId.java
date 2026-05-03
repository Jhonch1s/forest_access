package com.example.forest_access.biz.dao.entities.embeddables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EmpleadoHabilitacionId implements Serializable {

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_habilitacion")
    private Integer idHabilitacion;

    // constructor vacío obligatorio
    public EmpleadoHabilitacionId() {}

    public EmpleadoHabilitacionId(Integer idEmpleado, Integer idHabilitacion) {
        this.idEmpleado = idEmpleado;
        this.idHabilitacion = idHabilitacion;
    }

    // equals y hashCode obligatorios: JPA los usa para comparar identidad
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpleadoHabilitacionId)) return false;
        EmpleadoHabilitacionId that = (EmpleadoHabilitacionId) o;
        return Objects.equals(idEmpleado, that.idEmpleado) &&
                Objects.equals(idHabilitacion, that.idHabilitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, idHabilitacion);
    }

    // getters y setters
}