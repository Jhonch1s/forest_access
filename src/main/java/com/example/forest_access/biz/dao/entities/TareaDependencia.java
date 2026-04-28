package com.example.forest_access.biz.dao.entities;

import com.example.forest_access.biz.dao.entities.embeddables.TareaDependenciaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tarea_dependencia")
public class TareaDependencia {

    @EmbeddedId
    private TareaDependenciaId id;

    @ManyToOne
    @MapsId("idTareaPosterior")
    @JoinColumn(name = "id_tarea_posterior")
    private Tarea tareaPosterior;

    @ManyToOne
    @MapsId("idTareaAnterior")
    @JoinColumn(name = "id_tarea_anterior")
    private Tarea tareaAnterior;

    @Column(name = "dias_espera_minimo")
    private Integer diasEsperaMinimo = 0;

    // getters y setters
}