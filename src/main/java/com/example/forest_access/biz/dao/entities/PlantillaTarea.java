package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plantilla_tarea")
public class PlantillaTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;

    @ManyToOne
    @JoinColumn(name = "id_catalogo_tarea", nullable = false)
    private CatalogoTarea catalogoTarea;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // getters y setters
}
