package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Rodal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RodalRepository extends JpaRepository<Rodal, Integer> {
    List<Rodal> findByCampo(Campo campo);
    Optional<Rodal> findByNombre(String nombre);
    List<Rodal> findByCampo_IdCampo(Integer idCampo);
}