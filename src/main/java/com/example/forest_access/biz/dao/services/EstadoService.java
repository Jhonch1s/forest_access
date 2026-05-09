package com.example.forest_access.biz.dao.services;


import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.repositories.EstadoRepository;
import com.example.forest_access.dto.EstadoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadoService {

    private final EstadoRepository estadorepo;

    @Transactional(readOnly = true)
    public List<EstadoDTO> MostrarEstados() {
        return estadorepo.findAll().stream().map( e->{
            EstadoDTO estado = new EstadoDTO();
            estado.setNombre(e.getNombre());
            return estado;
        }).toList();
    }

}
