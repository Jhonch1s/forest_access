package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(nullable = false)
    private String nombre;

    private String contenido;

    private String concentracion;

    @Column(name = "unidad_base")
    private String unidadBase;                   // "litro", "kg"

    // getters y setters
}
