package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cuadrilla")
public class Cuadrilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuadrilla")
    private Integer idCuadrilla;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private Boolean activa = true;

    // getters y setters
}