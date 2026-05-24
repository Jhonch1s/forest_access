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
    @JoinColumn(name = "id_asignacion", nullable = false)
    private AsignacionTratamiento asignacionTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_catalogo_tarea", nullable = false)
    private CatalogoTarea catalogoTarea;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(precision = 4, scale = 2)
    private BigDecimal horas;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
