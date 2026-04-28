package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByUnidadBase(String unidadBase);
}
