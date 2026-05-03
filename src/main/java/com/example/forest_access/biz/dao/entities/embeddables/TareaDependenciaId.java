package com.example.forest_access.biz.dao.entities.embeddables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TareaDependenciaId implements Serializable {

    @Column(name = "id_tarea_posterior")
    private Integer idTareaPosterior;

    @Column(name = "id_tarea_anterior")
    private Integer idTareaAnterior;

    public TareaDependenciaId() {}

    public TareaDependenciaId(Integer idTareaPosterior, Integer idTareaAnterior) {
        this.idTareaPosterior = idTareaPosterior;
        this.idTareaAnterior = idTareaAnterior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TareaDependenciaId)) return false;
        TareaDependenciaId that = (TareaDependenciaId) o;
        return Objects.equals(idTareaPosterior, that.idTareaPosterior) &&
                Objects.equals(idTareaAnterior, that.idTareaAnterior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTareaPosterior, idTareaAnterior);
    }

    // getters y setters
}
