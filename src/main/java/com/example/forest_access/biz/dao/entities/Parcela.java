package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "parcela")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela")
    private Integer idParcela;

    @ManyToOne
    @JoinColumn(name = "id_rodal", nullable = false)
    private Rodal rodal;

    private String nombre;

    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    @Column(name = "tipo_cultivo")
    private String tipoCultivo;

    @Column(name = "anio_plantacion")
    private Integer anioPlantacion;

    @Column(name = "coord_lat", precision = 10, scale = 7)
    private BigDecimal coordLat;

    @Column(name = "coord_lng", precision = 10, scale = 7)
    private BigDecimal coordLng;

    // getters y setters
}