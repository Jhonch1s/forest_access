package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tarea_asignada")
public class TareaAsignada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea_asignada")
    private Integer idTareaAsignada;

    @ManyToOne
    @JoinColumn(name = "id_asignacion", nullable = false)
    private AsignacionTratamiento asignacionTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_cuadrilla", nullable = false)
    private Cuadrilla cuadrilla;

    @ManyToOne
    @JoinColumn(name = "id_catalogo_tarea", nullable = false)
    private CatalogoTarea catalogoTarea;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDate fechaLimite;
}
