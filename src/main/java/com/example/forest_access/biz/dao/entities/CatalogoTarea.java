package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "catalogo_tarea")
public class CatalogoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo_tarea")
    private Integer idCatalogoTarea;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "requiere_habilitacion")
    private Habilitacion requiereHabilitacion;   // nullable: no toda tarea exige carné

    // getters y setters
}
