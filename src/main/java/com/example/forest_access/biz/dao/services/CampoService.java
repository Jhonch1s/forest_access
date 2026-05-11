package com.example.forest_access.biz.dao.services;

import org.springframework.beans.BeanUtils;
import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.CampoRepository;
import com.example.forest_access.dto.CampoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
        return campoRepository.findAll().stream().map( c ->{
            Campo campo = new Campo();
            BeanUtils.copyProperties(c,campo);
//            campo.setNombre(c.getNombre());
//            campo.setPadron(c.getPadron());
//            campo.setSuperficieTotal(c.getSuperficieTotal());
//            campo.setCoordLat(c.getCoordLat());
//            campo.setCoordLng(c.getCoordLng());
            return campo;
        }).toList();
    }

    @Transactional
    public Campo createCampo(CampoDTO campo){
        Campo c = new Campo();
        c.setNombre(campo.getNombre());
        c.setPadron(campo.getPadron());
        c.setSuperficieTotal(campo.getSuperficieTotal());
        c.setCoordLat(campo.getCoordLat());
        c.setCoordLng(campo.getCoordLng());
        if (campoRepository.findByNombre(c.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un campo con el nombre: " + c.getNombre());
        }
        if (campoRepository.findByPadron(c.getPadron()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un campo con ese Padron: " + c.getPadron());
        }

        return campoRepository.save(c);
    }

    @Transactional
    public CampoDTO updateCampo(Integer id, CampoDTO campo){
        Campo existente = findById(id);
        if (!Objects.equals(campo.getNombre(), existente.getNombre())) {
            Campo campoConMismoNombre = campoRepository.findByNombre(campo.getNombre())
                .orElseThrow(() -> new RuntimeException("campo no encotrado"));
            if (campoConMismoNombre != null && !campoConMismoNombre.getIdCampo().equals(id)) {
                throw new RuntimeException("Ya existe un campo con el nombre: " + campo.getNombre());
            }
            existente.setNombre(campo.getNombre());
        }
        if (!Objects.equals(campo.getPadron(), existente.getPadron())) {
            Campo campoConMismoPadron = campoRepository.findByPadron(campo.getPadron())
                    .orElseThrow(() -> new RuntimeException("padron no encontrado"));
            if (campoConMismoPadron != null && !campoConMismoPadron.getIdCampo().equals(id)) {
                throw new RuntimeException("Ya existe un campo con el padron: " + campo.getPadron());
            }
            existente.setNombre(campo.getNombre());
        }

        existente.setPadron(campo.getPadron());
        existente.setSuperficieTotal(campo.getSuperficieTotal());
        existente.setCoordLat(campo.getCoordLat());
        existente.setCoordLng(campo.getCoordLng());
        campoRepository.save(existente);
        return campo;
    }

    @Transactional
    public CampoDTO deleteCampo(Integer id){
        Campo existente = findById(id);
        CampoDTO c = new CampoDTO();
        BeanUtils.copyProperties(existente,c);
        campoRepository.delete(existente);
        return c;
    }
}
