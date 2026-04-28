package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String cedula;

    @Column(unique = true)
    private String telefono;

    @Column(unique = true)
    private String email;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEmpleado categoria;

    // getters y setters
}
