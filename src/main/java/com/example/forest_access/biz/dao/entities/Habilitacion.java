package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "habilitacion")
public class Habilitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilitacion")
    private Integer idHabilitacion;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // getters y setters
}