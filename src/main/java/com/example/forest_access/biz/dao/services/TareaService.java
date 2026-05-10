package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.TareaResponse;
import com.example.forest_access.biz.dao.entities.*;
import com.example.forest_access.biz.dao.repositories.*;
import com.example.forest_access.dto.TareaDTO;
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
    public TareaResponse create(TareaDTO dto) {
        Tarea nueva = new Tarea();
        nueva.setFechaCreacion(LocalDate.now());
        updateEntityFromDTO(nueva, dto);
        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public TareaResponse update(Integer id, TareaDTO dto) {
        Tarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        updateEntityFromDTO(existente, dto);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No existe");
        repository.deleteById(id);
    }

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

    private void updateEntityFromDTO(Tarea entidad, TareaDTO dto) {
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setObservaciones(dto.getObservaciones());
        entidad.setHoras(dto.getHoras());
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFinEstimada(dto.getFechaFinEstimada());
        entidad.setFechaFinalizacion(dto.getFechaFinalizacion());

        if (dto.getEmpleado() != null && dto.getEmpleado().getIdEmpleado() != null) {
            entidad.setEmpleado(empleadoRepository.findById(dto.getEmpleado().getIdEmpleado()).orElse(null));
        } else {
            entidad.setEmpleado(null);
        }

        if (dto.getEstado() != null && dto.getEstado().getIdEstado() != null) {
            entidad.setEstado(estadoRepository.findById(dto.getEstado().getIdEstado()).orElse(null));
        } else {
            entidad.setEstado(null);
        }

        if (dto.getCatalogoTarea() != null && dto.getCatalogoTarea().getIdCatalogoTarea() != null) {
            entidad.setCatalogoTarea(catalogoRepository.findById(dto.getCatalogoTarea().getIdCatalogoTarea()).orElse(null));
        } else {
            entidad.setCatalogoTarea(null);
        }

        if (dto.getPlantilla() != null && dto.getPlantilla().getIdPlantilla() != null) {
            entidad.setPlantilla(plantillaRepository.findById(dto.getPlantilla().getIdPlantilla()).orElse(null));
        } else {
            entidad.setPlantilla(null);
        }

        if (dto.getHistoricoTratamiento() != null && dto.getHistoricoTratamiento().getIdHistorico() != null) {
            entidad.setHistoricoTratamiento(historicoRepository.findById(dto.getHistoricoTratamiento().getIdHistorico()).orElse(null));
        } else {
            entidad.setHistoricoTratamiento(null);
        }
    }

    private TareaResponse mapToResponse(Tarea t) {
        TareaResponse res = new TareaResponse();
        res.setIdTarea(t.getIdTarea());
        res.setDescripcion(t.getDescripcion());
        res.setHoras(t.getHoras());
        res.setFechaFinalizacion(t.getFechaFinalizacion());

        if (t.getEmpleado() != null) res.setNombreEmpleado(t.getEmpleado().getNombre());
        if (t.getEstado() != null) res.setNombreEstado(t.getEstado().getNombre());
        if (t.getCatalogoTarea() != null) res.setNombreTareaCatalogo(t.getCatalogoTarea().getNombre());

        return res;
    }
}