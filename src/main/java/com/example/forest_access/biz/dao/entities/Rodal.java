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
@Table(name = "rodal")
public class Rodal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rodal")
    private Integer idRodal;

    @ManyToOne
    @JoinColumn(name = "id_campo", nullable = false)
    private Campo campo;

    private String nombre;

    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    @Column(name = "coord_lat", precision = 10, scale = 7)
    private BigDecimal coordLat;

    @Column(name = "coord_lng", precision = 10, scale = 7)
    private BigDecimal coordLng;

    // getters y setters
}