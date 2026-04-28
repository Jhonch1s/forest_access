package com.example.forest_access.biz.dao.entities.embeddables;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TratamientoDependenciaId implements Serializable {

    @Column(name = "id_tratamiento_posterior")
    private Integer idTratamientoPosterior;

    @Column(name = "id_tratamiento_anterior")
    private Integer idTratamientoAnterior;

    public TratamientoDependenciaId() {}

    public TratamientoDependenciaId(Integer idTratamientoPosterior, Integer idTratamientoAnterior) {
        this.idTratamientoPosterior = idTratamientoPosterior;
        this.idTratamientoAnterior = idTratamientoAnterior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TratamientoDependenciaId)) return false;
        TratamientoDependenciaId that = (TratamientoDependenciaId) o;
        return Objects.equals(idTratamientoPosterior, that.idTratamientoPosterior) &&
                Objects.equals(idTratamientoAnterior, that.idTratamientoAnterior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTratamientoPosterior, idTratamientoAnterior);
    }

    // getters y setters
}
