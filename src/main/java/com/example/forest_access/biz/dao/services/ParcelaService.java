package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.repositories.ParcelaRepository;
import com.example.forest_access.biz.dao.repositories.RodalRepository;
import com.example.forest_access.dto.ParcelaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ParcelaService {

    private ParcelaRepository parcelaRepository;
    private RodalRepository rodalrepo;

    @Transactional(readOnly = true)
    public Parcela findById(Integer id) {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada con id: " + id));
    }

    @Transactional
    public List<ParcelaDTO> mostrarParcelas() {

        return parcelaRepository.findAll().stream().map(p ->{
            ParcelaDTO parcela = new ParcelaDTO();
            parcela.setNombre(p.getNombre());
            parcela.setArea(p.getArea());
            parcela.setTipoCultivo(p.getTipoCultivo());
            parcela.setAnioPlantacion(p.getAnioPlantacion());
            parcela.setCoordLat(p.getCoordLat());
            parcela.setCoordLng(p.getCoordLng());
            parcela.setIdRodal(p.getRodal().getIdRodal());
            return parcela;
        }).toList();
    }

    @Transactional
    public Parcela createParcela(ParcelaDTO p){
        if (parcelaRepository.findByNombre(p.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una parcela con el nombre: " + p.getNombre());
        }
        Parcela parcela = new Parcela();
        BeanUtils.copyProperties(p,parcela);
        Rodal r = rodalrepo.findById(p.getIdRodal())
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));
        parcela.setRodal(r);
        parcelaRepository.save(parcela);
        return parcela;
    }

    @Transactional
    public ParcelaDTO updateParcela(Integer id,ParcelaDTO parcela){
        Parcela parcelaExistente = findById(id);
        if (!Objects.equals(parcela.getNombre(), parcelaExistente.getNombre())) {
            Parcela parcelaConMismoNombre = parcelaRepository.findByNombre(parcela.getNombre())
                    .orElseThrow(() -> new RuntimeException("rodal no encotrado"));
            if (parcelaConMismoNombre != null && !parcelaConMismoNombre.getIdParcela().equals(id)) {
                throw new RuntimeException("Ya existe una parcela con el nombre: " + parcela.getNombre());
            }
            parcelaExistente.setNombre(parcela.getNombre());
        }


        parcelaExistente.setArea(parcela.getArea());
        Rodal r = rodalrepo.findById(parcela.getIdRodal())
                        .orElseThrow(() -> new RuntimeException("rodal no encontrado"));
        parcelaExistente.setRodal(r);
        parcelaExistente.setTipoCultivo(parcela.getTipoCultivo());
        parcelaExistente.setAnioPlantacion(parcela.getAnioPlantacion());
        parcelaExistente.setCoordLat(parcela.getCoordLat());
        parcelaExistente.setCoordLng(parcela.getCoordLng());
        parcelaRepository.save(parcelaExistente);
        return parcela;
    }

    @Transactional
    public ParcelaDTO deleteParcela(Integer id){
        Parcela parcelaExistente = findById(id);
        ParcelaDTO p = new ParcelaDTO();
        BeanUtils.copyProperties(parcelaExistente,p);
        parcelaRepository.delete(parcelaExistente);
        return p;
    }
}
