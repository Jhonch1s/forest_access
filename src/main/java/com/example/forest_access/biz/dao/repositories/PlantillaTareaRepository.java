package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.CatalogoTarea;
import com.example.forest_access.biz.dao.entities.PlantillaTarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantillaTareaRepository extends JpaRepository<PlantillaTarea, Integer> {
    List<PlantillaTarea> findByCatalogoTarea(CatalogoTarea catalogoTarea);
    List<PlantillaTarea> findByCatalogoTarea_IdCatalogoTarea(Integer idCatalogoTarea);
    Optional<PlantillaTarea> findByNombre(String nombre);
}
