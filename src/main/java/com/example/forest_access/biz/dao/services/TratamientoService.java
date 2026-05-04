package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TratamientoService {

    private TratamientoRepository tratamientoRepository;

    public List<Tratamiento> findAll() {
        return tratamientoRepository.findAll();
    }

    @Transactional
    public Tratamiento create(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Transactional
    public Tratamiento update(Integer idTratamiento, Tratamiento tratamientoActualizado) {

        Tratamiento tratamientoExistente = tratamientoRepository.findById(idTratamiento)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " + idTratamiento));

        tratamientoExistente.setNombre(tratamientoActualizado.getNombre());
        tratamientoExistente.setDescripcion(tratamientoActualizado.getDescripcion());
        return tratamientoRepository.save(tratamientoExistente);
    }

    @Transactional
    public void delete(Integer idTratamiento) {
        if (!tratamientoRepository.existsById(idTratamiento)) {
            throw new RuntimeException("Tratamiento no encontrado con id: " + idTratamiento);
        }
        tratamientoRepository.deleteById(idTratamiento);
    }

}
