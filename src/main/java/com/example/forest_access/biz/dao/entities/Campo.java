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
@Table(name = "campo")
public class Campo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campo")
    private Integer idCampo;

    @Column(nullable = false)
    private String nombre;

    private String padron;

    @Column(name = "superficie_total", precision = 10, scale = 2)
    private BigDecimal superficieTotal;

    @Column(name = "coord_lat", precision = 11, scale = 8)
    private BigDecimal coordLat;

    @Column(name = "coord_lng", precision = 11, scale = 8)
    private BigDecimal coordLng;

    // getters y setters
}
