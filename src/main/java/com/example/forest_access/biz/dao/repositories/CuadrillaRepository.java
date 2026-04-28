package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuadrillaRepository extends JpaRepository<Cuadrilla, Integer> {
    Optional<Cuadrilla> findByNombre(String nombre);
    List<Cuadrilla> findByActiva(Boolean activa);
}