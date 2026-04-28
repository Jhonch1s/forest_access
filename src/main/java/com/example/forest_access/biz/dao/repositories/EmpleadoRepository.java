package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByCedula(String cedula);
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByActivo(Boolean activo);
    List<Empleado> findByCategoria(CategoriaEmpleado categoria);
}