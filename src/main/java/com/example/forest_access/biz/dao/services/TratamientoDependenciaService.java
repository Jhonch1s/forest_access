package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.TratamientoDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TratamientoDependenciaId;
import com.example.forest_access.biz.dao.repositories.TratamientoDependenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TratamientoDependenciaService {

    private final TratamientoDependenciaRepository repository;

    @Transactional(readOnly = true)
    public List<TratamientoDependencia> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TratamientoDependencia findById(Integer idPosterior, Integer idAnterior) {
        TratamientoDependenciaId id = new TratamientoDependenciaId(idPosterior, idAnterior);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la dependencia entre los tratamientos especificados."));
    }

    @Transactional
    public TratamientoDependencia create(TratamientoDependencia dependencia) {
        // Al usar @EmbeddedId y @MapsId, es vital construir el ID antes de persistir
        if (dependencia.getId() == null) {
            dependencia.setId(new TratamientoDependenciaId(
                    dependencia.getTratamientoPosterior().getIdTratamiento(),
                    dependencia.getTratamientoAnterior().getIdTratamiento()
            ));
        }

        if (repository.existsById(dependencia.getId())) {
            throw new IllegalArgumentException("Esta regla de dependencia ya está registrada.");
        }

        // Validación lógica: no puede depender de sí mismo
        if (dependencia.getId().getIdTratamientoPosterior().equals(dependencia.getId().getIdTratamientoAnterior())) {
            throw new IllegalArgumentException("Un tratamiento no puede ser dependiente de sí mismo.");
        }

        return repository.save(dependencia);
    }

    @Transactional
    public TratamientoDependencia update(Integer idPosterior, Integer idAnterior, TratamientoDependencia datos) {
        // En claves compuestas, el update suele enfocarse en los campos que NO son parte de la PK
        TratamientoDependencia existente = findById(idPosterior, idAnterior);

        existente.setDiasEsperaMinimo(datos.getDiasEsperaMinimo());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer idPosterior, Integer idAnterior) {
        TratamientoDependenciaId id = new TratamientoDependenciaId(idPosterior, idAnterior);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: la dependencia no existe.");
        }
        repository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<TratamientoDependencia> findPorTratamientoPosterior(Integer idTratamiento) {
        return repository.findByTratamientoPosterior_IdTratamiento(idTratamiento);
    }

    @Transactional(readOnly = true)
    public List<TratamientoDependencia> findPorTratamientoAnterior(Integer idTratamiento) {
        return repository.findByTratamientoAnterior_IdTratamiento(idTratamiento);
    }
}