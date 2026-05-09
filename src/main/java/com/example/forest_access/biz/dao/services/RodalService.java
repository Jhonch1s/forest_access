package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.repositories.CampoRepository;
import com.example.forest_access.biz.dao.repositories.RodalRepository;
import com.example.forest_access.dto.RodalDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RodalService {

    private RodalRepository repository;
    private CampoRepository campoRepository;

    @Transactional(readOnly = true)
    public Rodal findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rodal no encontrado con id: " + id));
    }

    @Transactional
    public List<RodalDTO> mostrarRodales() {

        return repository.findAll().stream().map( r ->{
            RodalDTO rodal = new RodalDTO();
            BeanUtils.copyProperties(r, rodal);
            rodal.setIdCampo(r.getCampo().getIdCampo());
            return rodal;
        }).toList();
    }

    @Transactional
    public Rodal createRodal(RodalDTO rodal) {
        if (repository.findByNombre(rodal.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un rodal con el nombre: " + rodal.getNombre());
        }
        Rodal r = new Rodal();
        BeanUtils.copyProperties(rodal, r);
        Campo c = campoRepository.findById(rodal.getIdCampo())
                .orElseThrow(() -> new EntityNotFoundException("Campo no encontrado"));
        r.setCampo(c);
        repository.save(r);
        return r;
    }

    @Transactional
    public RodalDTO updateRodal(Integer id,RodalDTO rodal) {
        Rodal rodalExistente = findById(id);

        if (!Objects.equals(rodal.getNombre(), rodalExistente.getNombre())) {
            Rodal rodalConMismoNombre = repository.findByNombre(rodal.getNombre())
                    .orElseThrow(() -> new RuntimeException("rodal no encotrado"));
            if (rodalConMismoNombre != null && !rodalConMismoNombre.getIdRodal().equals(id)) {
                throw new RuntimeException("Ya existe un rodal con el nombre: " + rodal.getNombre());
            }
            rodalExistente.setNombre(rodal.getNombre());
        }

        rodalExistente.setArea(rodal.getArea());
        rodalExistente.setCoordLat(rodal.getCoordLat());
        rodalExistente.setCoordLng(rodal.getCoordLng());
        Campo c = campoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campo no encontrado"));

        rodalExistente.setCampo(c);

        repository.save(rodalExistente);

        return rodal;
    }

    @Transactional
    public RodalDTO deleteRodal(Integer id) {
        Rodal rodalExistente = findById(id);
        RodalDTO r = new RodalDTO();
        BeanUtils.copyProperties(rodalExistente,r);
        r.setIdCampo(rodalExistente.getCampo().getIdCampo());
        repository.delete(rodalExistente);
        return r;
    }
}
