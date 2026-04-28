package com.example.forest_access.biz.dao.entities;

import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoHabilitacionId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleado_habilitacion")
public class EmpleadoHabilitacion {

    @EmbeddedId
    private EmpleadoHabilitacionId id;

    // @MapsId indica qué campo del EmbeddedId representa esta relación
    @ManyToOne
    @MapsId("idEmpleado")
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne
    @MapsId("idHabilitacion")
    @JoinColumn(name = "id_habilitacion")
    private Habilitacion habilitacion;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    // getters y setters
}