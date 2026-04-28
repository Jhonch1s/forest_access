package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "registro_diario",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id_empleado", "fecha"}  // un registro por empleado por día
        )
)
public class RegistroDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal jornales = BigDecimal.ONE;  // permite 0.5 para media jornada

    @Column(precision = 10, scale = 2)
    private BigDecimal adelanto = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // getters y setters
}