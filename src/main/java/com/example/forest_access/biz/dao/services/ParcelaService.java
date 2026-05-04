package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.repositories.ParcelaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ParcelaService {

    private ParcelaRepository parcelaRepository;

    @Transactional(readOnly = true)
    public Parcela findById(Integer id) {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada con id: " + id));
    }

    @Transactional
    public List<Parcela> mostrarParcelas() {
        return parcelaRepository.findAll();
    }

    @Transactional
    public Parcela createParcela(Parcela parcela){
        return parcelaRepository.save(parcela);
    }

    @Transactional
    public Parcela updateParcela(Integer id,Parcela parcela){
        Parcela parcelaExistente = findById(id);

        parcelaExistente.setNombre(parcela.getNombre());
        parcelaExistente.setArea(parcela.getArea());
        parcelaExistente.setRodal(parcela.getRodal());
        parcelaExistente.setTipoCultivo(parcela.getTipoCultivo());
        parcelaExistente.setAnioPlantacion(parcela.getAnioPlantacion());
        parcelaExistente.setCoordLat(parcela.getCoordLat());
        parcelaExistente.setCoordLng(parcela.getCoordLng());
        return parcelaRepository.save(parcelaExistente);
    }

    @Transactional
    public Parcela deleteParcela(Integer id){
        Parcela parcelaExistente = findById(id);
        parcelaRepository.delete(parcelaExistente);
        return parcelaExistente;
    }
}
