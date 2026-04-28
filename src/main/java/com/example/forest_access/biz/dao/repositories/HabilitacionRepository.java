package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HabilitacionRepository extends JpaRepository<Habilitacion, Integer> {
    Optional<Habilitacion> findByNombre(String nombre);
}
