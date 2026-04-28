package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaEmpleadoRepository extends JpaRepository<CategoriaEmpleado, Integer> {
    Optional<CategoriaEmpleado> findByNombre(String nombre);
}