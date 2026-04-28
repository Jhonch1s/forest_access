package com.example.forest_access.biz.dao.entities;

import com.example.forest_access.biz.dao.entities.embeddables.TratamientoDependenciaId;
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
@Table(name = "tratamiento_dependencia")
public class TratamientoDependencia {

    @EmbeddedId
    private TratamientoDependenciaId id;

    @ManyToOne
    @MapsId("idTratamientoPosterior")
    @JoinColumn(name = "id_tratamiento_posterior")
    private Tratamiento tratamientoPosterior;

    @ManyToOne
    @MapsId("idTratamientoAnterior")
    @JoinColumn(name = "id_tratamiento_anterior")
    private Tratamiento tratamientoAnterior;

    @Column(name = "dias_espera_minimo", nullable = false)
    private Integer diasEsperaMinimo = 0;

    // getters y setters
}
