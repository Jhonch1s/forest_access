package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer idTarea;

    @ManyToOne
    @JoinColumn(name = "id_catalogo_tarea", nullable = false)
    private CatalogoTarea catalogoTarea;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_historico", nullable = false)
    private HistoricoTratamiento historicoTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_plantilla")
    private PlantillaTarea plantilla;            // nullable: no siempre viene de una plantilla

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion = LocalDate.now();

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private LocalDate fechaFinEstimada;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // obligatorios al finalizar
    @Column(precision = 4, scale = 2)
    private BigDecimal horas;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // getters y setters
}