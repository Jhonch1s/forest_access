package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Rodal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {
    List<Parcela> findByRodal(Rodal rodal);
    List<Parcela> findByRodal_IdRodal(Integer idRodal);
    List<Parcela> findByRodal_Campo_IdCampo(Integer idCampo);  // todas las parcelas de un campo
}