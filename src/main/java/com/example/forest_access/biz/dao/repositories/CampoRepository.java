package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Campo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampoRepository extends JpaRepository<Campo, Integer> {
    Optional<Campo> findByNombre(String nombre);
    Optional<Campo> findByPadron(String padron);
}
