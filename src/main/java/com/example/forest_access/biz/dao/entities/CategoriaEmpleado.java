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
@Table(name = "categoria_empleado")
public class CategoriaEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(name = "valor_jornal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorJornal;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // getters y setters
}
