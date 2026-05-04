package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Rodal;
import com.example.forest_access.biz.dao.repositories.RodalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RodalService {

    private RodalRepository repository;

    @Transactional(readOnly = true)
    public Rodal findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rodal no encontrado con id: " + id));
    }

    @Transactional
    public List<Rodal> mostrarRodales() {
        return repository.findAll();
    }

    @Transactional
    public Rodal createRodal(Rodal rodal) {
        return repository.save(rodal);
    }

    @Transactional
    public Rodal updateRodal(Integer id,Rodal rodal) {
        Rodal rodalExistente = findById(id);

        rodalExistente.setNombre(rodal.getNombre());
        rodalExistente.setArea(rodal.getArea());
        rodalExistente.setCoordLat(rodal.getCoordLat());
        rodalExistente.setCoordLng(rodal.getCoordLng());
        rodalExistente.setCampo(rodal.getCampo());

        return repository.save(rodalExistente);
    }

    @Transactional
    public Rodal deleteRodal(Integer id) {
        Rodal rodalExistente = findById(id);
        repository.delete(rodalExistente);
        return rodalExistente;
    }
}
