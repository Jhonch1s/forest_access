package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "historico_tratamiento")
public class HistoricoTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico")
    private Integer idHistorico;

    @ManyToOne
    @JoinColumn(name = "id_parcela", nullable = false)
    private Parcela parcela;

    @ManyToOne
    @JoinColumn(name = "id_tratamiento", nullable = false)
    private Tratamiento tratamiento;

    @ManyToOne
    @JoinColumn(name = "id_cuadrilla", nullable = false)
    private Cuadrilla cuadrilla;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // getters y setters
}
