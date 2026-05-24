package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.TareaAsignadaRequest;
import com.example.forest_access.api.controllers.response.TareaAsignadaResponse;
import com.example.forest_access.biz.dao.entities.*;
import com.example.forest_access.biz.dao.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TareaAsignadaService {

    private final TareaAsignadaRepository repository;
    private final AsignacionTratamientoRepository asignacionRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final CatalogoTareaRepository catalogoRepository;

    @Transactional(readOnly = true)
    public List<TareaAsignadaResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TareaAsignadaResponse findById(Integer id) {
        TareaAsignada ta = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea asignada no encontrada con ID: " + id));
        return mapToResponse(ta);
    }

    @Transactional(readOnly = true)
    public List<TareaAsignadaResponse> findByCuadrillaVigentes(Integer idCuadrilla) {
        return repository.findByCuadrilla_IdCuadrillaAndFechaLimiteGreaterThanEqual(
                        idCuadrilla, LocalDate.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaAsignadaResponse> findByAsignacionAndCuadrillaVigentes(Long idAsignacion, Integer idCuadrilla) {
        return repository.findByAsignacionTratamiento_IdAsignacionAndCuadrilla_IdCuadrillaAndFechaLimiteGreaterThanEqual(
                        idAsignacion, idCuadrilla, LocalDate.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TareaAsignadaResponse create(TareaAsignadaRequest request) {
        TareaAsignada nueva = new TareaAsignada();
        updateEntityFromRequest(nueva, request);
        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public TareaAsignadaResponse update(Integer id, TareaAsignadaRequest request) {
        TareaAsignada existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea asignada no encontrada"));
        updateEntityFromRequest(existente, request);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: tarea asignada no encontrada");
        }
        repository.deleteById(id);
    }

    private void updateEntityFromRequest(TareaAsignada entidad, TareaAsignadaRequest request) {
        entidad.setDescripcion(request.getDescripcion());
        entidad.setFechaLimite(request.getFechaLimite());

        if (request.getIdAsignacion() != null) {
            entidad.setAsignacionTratamiento(
                    asignacionRepository.findById(request.getIdAsignacion())
                            .orElseThrow(() -> new EntityNotFoundException("Asignación no encontrada")));
        }

        if (request.getIdCuadrilla() != null) {
            entidad.setCuadrilla(
                    cuadrillaRepository.findById(request.getIdCuadrilla())
                            .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada")));
        }

        if (request.getIdCatalogoTarea() != null) {
            entidad.setCatalogoTarea(
                    catalogoRepository.findById(request.getIdCatalogoTarea())
                            .orElseThrow(() -> new EntityNotFoundException("Catálogo de tarea no encontrado")));
        }
    }

    private TareaAsignadaResponse mapToResponse(TareaAsignada ta) {
        TareaAsignadaResponse.TareaAsignadaResponseBuilder builder = TareaAsignadaResponse.builder()
                .idTareaAsignada(ta.getIdTareaAsignada())
                .descripcion(ta.getDescripcion())
                .fechaLimite(ta.getFechaLimite());

        if (ta.getAsignacionTratamiento() != null) {
            builder.idAsignacion(ta.getAsignacionTratamiento().getIdAsignacion());
            if (ta.getAsignacionTratamiento().getParcela() != null) {
                builder.nombreParcela(ta.getAsignacionTratamiento().getParcela().getNombre());
            }
        }

        if (ta.getCuadrilla() != null) {
            builder.idCuadrilla(ta.getCuadrilla().getIdCuadrilla());
            builder.nombreCuadrilla(ta.getCuadrilla().getNombre());
        }

        if (ta.getCatalogoTarea() != null) {
            builder.idCatalogoTarea(ta.getCatalogoTarea().getIdCatalogoTarea());
            builder.nombreCatalogoTarea(ta.getCatalogoTarea().getNombre());
        }

        return builder.build();
    }
}
