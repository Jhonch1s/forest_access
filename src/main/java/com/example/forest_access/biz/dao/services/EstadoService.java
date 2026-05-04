package com.example.forest_access.biz.dao.services;


import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.repositories.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadoService {

    private final EstadoRepository estadorepo;

    @Transactional(readOnly = true)
    public List<Estado> MostrarEstados() { return estadorepo.findAll();}

}
