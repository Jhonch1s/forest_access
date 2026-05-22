package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
import com.example.forest_access.dto.HabilitacionDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HabilitacionService {

    private final HabilitacionRepository habilitacionrepo;

    @Transactional
    public List<HabilitacionDTO> mostrarHabilitaciones(){
        return  habilitacionrepo.findAll().stream().map( h ->{
            HabilitacionDTO habilitacion = new HabilitacionDTO();
            habilitacion.setIdHabilitacion(h.getIdHabilitacion());
            habilitacion.setNombre(h.getNombre());
            habilitacion.setDescripcion(h.getDescripcion());
            return habilitacion;
        }).toList();
    }

    @Transactional
    public Habilitacion createHabilitacion(HabilitacionDTO habilitacion){
        Habilitacion h = new Habilitacion();
        h.setNombre(habilitacion.getNombre());
        h.setDescripcion(habilitacion.getDescripcion());

        habilitacionrepo.save(h);
        return h;
    }

    @Transactional
    public HabilitacionDTO updateHabilitacion(Integer id,HabilitacionDTO habilitacion){
        Habilitacion h = habilitacionrepo.findById(id)
                .orElseThrow( () -> new RuntimeException("Habilitacion no existente"));
        h.setNombre(habilitacion.getNombre());
        h.setDescripcion(habilitacion.getDescripcion());
        habilitacionrepo.save(h);
        HabilitacionDTO h2 = new HabilitacionDTO();
        BeanUtils.copyProperties(h,h2);
        return h2;
    }

    @Transactional
    public void deleteHabilitacion(Integer id){
        Habilitacion h = habilitacionrepo.findById(id)
                .orElseThrow( () -> new RuntimeException("Habilitacion no existente"));
        habilitacionrepo.delete(h);
    }
}
