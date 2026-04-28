package com.example.forest_access.biz.dao.entities;

import com.example.forest_access.biz.dao.entities.embeddables.EmpleadoCuadrillaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "empleado_cuadrilla")
public class EmpleadoCuadrilla {

    @EmbeddedId
    private EmpleadoCuadrillaId id;

    @ManyToOne
    @MapsId("idCuadrilla")
    @JoinColumn(name = "id_cuadrilla")
    private Cuadrilla cuadrilla;

    @ManyToOne
    @MapsId("idEmpleado")
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;                  // NULL = pertenece actualmente

    private String rol;                          // "Capataz", "Peón puntero", "Operario"

    // getters y setters
}