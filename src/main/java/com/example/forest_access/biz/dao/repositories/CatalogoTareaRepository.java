package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.Habilitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogoTareaRepository extends JpaRepository<CatalogoTarea, Integer> {
    Optional<CatalogoTarea> findByNombre(String nombre);
    List<CatalogoTarea> findByRequiereHabilitacionIsNull();         // tareas sin requisito
    List<CatalogoTarea> findByRequiereHabilitacion(Habilitacion h); // tareas que exigen ese carné
}
