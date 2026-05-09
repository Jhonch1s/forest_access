package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.TareaRequest;
import com.example.forest_access.api.controllers.response.TareaResponse;
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
public class TareaService {

    private final TareaRepository repository;
    private final EmpleadoRepository empleadoRepository;
    private final EstadoRepository estadoRepository;
    private final CatalogoTareaRepository catalogoRepository;
    private final PlantillaTareaRepository plantillaRepository;
    private final HistoricoTratamientoRepository historicoRepository;

    @Transactional(readOnly = true)
    public List<TareaResponse> findAll() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TareaResponse findById(Integer id) {
        Tarea tarea = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + id));
        return mapToResponse(tarea);
    }

    @Transactional
    public TareaResponse create(TareaRequest request) {
        Tarea nueva = new Tarea();
        nueva.setFechaCreacion(LocalDate.now());
        updateEntityFromRequest(nueva, request);
        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public TareaResponse update(Integer id, TareaRequest request) {
        Tarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        updateEntityFromRequest(existente, request);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No existe");
        repository.deleteById(id);
    }

    // --- Métodos de búsqueda especializados ---

    @Transactional(readOnly = true)
    public List<TareaResponse> findPorEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaResponse> findParaLiquidacion(Integer idEmpleado, LocalDate inicio, LocalDate fin) {
        Empleado e = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return repository.findByEmpleadoAndFechaFinalizacionBetween(e, inicio, fin).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    // --- MAPPERS (Traducción) ---

    private void updateEntityFromRequest(Tarea entidad, TareaRequest request) {
        entidad.setDescripcion(request.getDescripcion());
        entidad.setObservaciones(request.getObservaciones());
        entidad.setHoras(request.getHoras());
        entidad.setFechaInicio(request.getFechaInicio());
        entidad.setFechaFinEstimada(request.getFechaFinEstimada());
        entidad.setFechaFinalizacion(request.getFechaFinalizacion());

        // Resolución de Relaciones por ID
        if (request.getIdEmpleado() != null)
            entidad.setEmpleado(empleadoRepository.findById(request.getIdEmpleado()).orElse(null));

        if (request.getIdEstado() != null)
            entidad.setEstado(estadoRepository.findById(request.getIdEstado()).orElse(null));

        if (request.getIdCatalogoTarea() != null)
            entidad.setCatalogoTarea(catalogoRepository.findById(request.getIdCatalogoTarea()).orElse(null));

        if (request.getIdPlantilla() != null)
            entidad.setPlantilla(plantillaRepository.findById(request.getIdPlantilla()).orElse(null));

        if (request.getIdHistoricoTratamiento() != null)
            entidad.setHistoricoTratamiento(historicoRepository.findById(request.getIdHistoricoTratamiento()).orElse(null));
    }

    private TareaResponse mapToResponse(Tarea t) {
        TareaResponse res = new TareaResponse();
        res.setIdTarea(t.getIdTarea());
        res.setDescripcion(t.getDescripcion());
        res.setHoras(t.getHoras());
        res.setFechaFinalizacion(t.getFechaFinalizacion());

        // Nombres legibles para el Frontend
        if (t.getEmpleado() != null) res.setNombreEmpleado(t.getEmpleado().getNombre());
        if (t.getEstado() != null) res.setNombreEstado(t.getEstado().getNombre());
        if (t.getCatalogoTarea() != null) res.setNombreTareaCatalogo(t.getCatalogoTarea().getNombre());

        return res;
    }
}