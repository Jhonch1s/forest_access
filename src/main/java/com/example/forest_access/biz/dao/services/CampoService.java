package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.CampoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CampoService {

    private final CampoRepository campoRepository;

    @Transactional(readOnly = true)
    public Campo findById(Integer id) {
        return campoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campo no encontrado con id: " + id));
    }

    @Transactional(readOnly =true)
    public List<Campo> MostrarCampos(){
        return campoRepository.findAll();
    }

    @Transactional
    public Campo createCampo(Campo campo){
        if (campoRepository.findByNombre(campo.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un campo con el nombre: " + campo.getNombre());
        }

        return campoRepository.save(campo);
    }

    @Transactional
    public Campo updateCampo(Integer id, Campo campo){
        Campo existente = findById(id);

        existente.setNombre(campo.getNombre());
        existente.setPadron(campo.getPadron());
        existente.setSuperficieTotal(campo.getSuperficieTotal());
        existente.setCoordLat(campo.getCoordLat());
        existente.setCoordLng(campo.getCoordLng());
        return campoRepository.save(existente);
    }

    @Transactional
    public Campo deleteCampo(Integer id){
        Campo existente = findById(id);

        campoRepository.delete(existente);
        return existente;
    }
}
