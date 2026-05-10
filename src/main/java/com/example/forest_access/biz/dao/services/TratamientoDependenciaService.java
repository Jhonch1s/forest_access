package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.TratamientoDependenciaResponse;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.entities.TratamientoDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TratamientoDependenciaId;
import com.example.forest_access.biz.dao.repositories.TratamientoDependenciaRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import com.example.forest_access.dto.TratamientoDependenciaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TratamientoDependenciaService {

    private final TratamientoDependenciaRepository repository;
    private final TratamientoRepository tratamientoRepository;

    @Transactional(readOnly = true)
    public List<TratamientoDependenciaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TratamientoDependenciaResponse create(TratamientoDependenciaDTO dto) {
        Integer idAnterior = dto.getTratamientoAnterior().getIdTratamiento();
        Integer idPosterior = dto.getTratamientoPosterior().getIdTratamiento();

        if (idAnterior.equals(idPosterior)) {
            throw new IllegalArgumentException("Un tratamiento no puede depender de sí mismo");
        }

        TratamientoDependenciaId id = new TratamientoDependenciaId(idPosterior, idAnterior);

        if (repository.existsById(id)) {
            throw new IllegalArgumentException("Esta dependencia ya está registrada");
        }

        Tratamiento anterior = tratamientoRepository.findById(idAnterior)
                .orElseThrow(() -> new EntityNotFoundException("Tratamiento anterior no encontrado"));
        Tratamiento posterior = tratamientoRepository.findById(idPosterior)
                .orElseThrow(() -> new EntityNotFoundException("Tratamiento posterior no encontrado"));

        TratamientoDependencia nueva = new TratamientoDependencia();
        nueva.setId(id);
        nueva.setTratamientoPosterior(posterior);
        nueva.setTratamientoAnterior(anterior);
        nueva.setDiasEsperaMinimo(dto.getDiasEsperaMinimo());

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public void delete(Integer idAnterior, Integer idPosterior) {
        TratamientoDependenciaId id = new TratamientoDependenciaId(idPosterior, idAnterior);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Dependencia no encontrada");
        }
        repository.deleteById(id);
    }

    private TratamientoDependenciaResponse mapToResponse(TratamientoDependencia entidad) {
        TratamientoDependenciaResponse res = new TratamientoDependenciaResponse();
        res.setIdTratamientoAnterior(entidad.getId().getIdTratamientoAnterior());
        res.setNombreTratamientoAnterior(entidad.getTratamientoAnterior().getNombre());
        res.setIdTratamientoPosterior(entidad.getId().getIdTratamientoPosterior());
        res.setNombreTratamientoPosterior(entidad.getTratamientoPosterior().getNombre());
        res.setDiasEsperaMinimo(entidad.getDiasEsperaMinimo());
        return res;
    }
}