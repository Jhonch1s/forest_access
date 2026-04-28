package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.entities.TratamientoDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TratamientoDependenciaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoDependenciaRepository
        extends JpaRepository<TratamientoDependencia, TratamientoDependenciaId> {

    // qué tratamientos debo esperar antes de aplicar este
    List<TratamientoDependencia> findByTratamientoPosterior(Tratamiento tratamiento);
    List<TratamientoDependencia> findByTratamientoPosterior_IdTratamiento(Integer idTratamiento);

    // qué tratamientos bloquea este al ser aplicado
    List<TratamientoDependencia> findByTratamientoAnterior(Tratamiento tratamiento);
    List<TratamientoDependencia> findByTratamientoAnterior_IdTratamiento(Integer idTratamiento);
}
