package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
import com.example.forest_access.dto.HabilitacionDTO;
import lombok.AllArgsConstructor;
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
}
