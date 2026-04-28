package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
    Optional<Tratamiento> findByNombre(String nombre);
}