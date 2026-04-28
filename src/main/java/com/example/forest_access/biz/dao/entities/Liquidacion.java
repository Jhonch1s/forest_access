package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "liquidacion")
public class Liquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_liquidacion")
    private Integer idLiquidacion;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin", nullable = false)
    private LocalDate periodoFin;

    // snapshot: todos estos valores quedan fijos al momento de liquidar
    @Column(name = "total_jornales", nullable = false, precision = 6, scale = 2)
    private BigDecimal totalJornales;

    @Column(name = "valor_jornal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorJornal;             // copiado de categoria_empleado al liquidar

    @Column(name = "total_nominal", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalNominal;            // totalJornales × valorJornal

    @Column(name = "total_produccion", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalProduccion;         // generado por producción en el período

    @Column(name = "total_incentivo", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIncentivo;          // totalProduccion - totalNominal

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal adelantos = BigDecimal.ZERO;

    @Column(name = "total_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFinal;              // totalIncentivo - adelantos

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // getters y setters
}
