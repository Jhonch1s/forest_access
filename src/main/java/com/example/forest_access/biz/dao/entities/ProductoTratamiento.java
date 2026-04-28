package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "producto_tratamiento",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id_tratamiento", "id_producto"}
        )
)
public class ProductoTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_tratamiento")
    private Integer idProductoTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_tratamiento", nullable = false)
    private Tratamiento tratamiento;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal dosis;

    @Column(nullable = false)
    private String unidad;

    // getters y setters
}
