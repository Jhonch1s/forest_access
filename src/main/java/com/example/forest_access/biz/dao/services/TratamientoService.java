package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import com.example.forest_access.dto.TratamientoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TratamientoService {

    private TratamientoRepository tratamientoRepository;

    public List<TratamientoDTO> findAll() {
        return tratamientoRepository.findAll().stream().map(t ->{
            TratamientoDTO tratamiento = new TratamientoDTO();
            tratamiento.setNombre(t.getNombre());
            tratamiento.setDescripcion(t.getDescripcion());
            return tratamiento;
        }).toList();
    }

    @Transactional
    public Tratamiento create(TratamientoDTO tratamiento) {
        Tratamiento t = new Tratamiento();
        t.setNombre(tratamiento.getNombre());
        t.setDescripcion(tratamiento.getDescripcion());
        return tratamientoRepository.save(t);
    }

    @Transactional
    public TratamientoDTO update(Integer idTratamiento, TratamientoDTO tratamientoActualizado) {

        Tratamiento tratamientoExistente = tratamientoRepository.findById(idTratamiento)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " + idTratamiento));

        tratamientoExistente.setNombre(tratamientoActualizado.getNombre());
        tratamientoExistente.setDescripcion(tratamientoActualizado.getDescripcion());
        tratamientoRepository.save(tratamientoExistente);
        return tratamientoActualizado;
    }

    @Transactional
    public TratamientoDTO delete(Integer idTratamiento) {
        if (!tratamientoRepository.existsById(idTratamiento)) {
            throw new RuntimeException("Tratamiento no encontrado con id: " + idTratamiento);
        }
        Tratamiento t1 = new Tratamiento();
        TratamientoDTO t2 = new TratamientoDTO();
        t2.setNombre(t1.getNombre());
        t2.setDescripcion(t1.getDescripcion());
        tratamientoRepository.deleteById(idTratamiento);
        return t2;
    }

}
