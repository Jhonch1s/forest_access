package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Habilitacion;
import com.example.forest_access.biz.dao.repositories.HabilitacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HabilitacionService {

    private final HabilitacionRepository habilitacionrepo;

    @Transactional
    public List<Habilitacion> mostrarHabilitaciones(){
        return  habilitacionrepo.findAll();
    }

    @Transactional
    public Habilitacion createHabilitacion(Habilitacion habilitacion){
        return habilitacionrepo.save(habilitacion);
    }
}
