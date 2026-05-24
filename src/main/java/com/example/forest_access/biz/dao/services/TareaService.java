package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.TareaRequest;
import com.example.forest_access.api.controllers.response.TareaResponse;
import com.example.forest_access.biz.dao.entities.*;
import com.example.forest_access.biz.dao.repositories.*;
import com.example.forest_access.enums.EstadoAsignacion;
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
    private final AsignacionTratamientoRepository asignacionRepository;

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
    public TareaResponse create(TareaRequest req) {
        Tarea nueva = new Tarea();
        nueva.setFecha(req.getFecha() != null ? req.getFecha() : LocalDate.now());
        updateEntityFromRequest(nueva, req);
        Tarea guardada = repository.save(nueva);

        // Auto-transición: PLANIFICADO → EN_EJECUCION
        if (guardada.getAsignacionTratamiento() != null
                && guardada.getAsignacionTratamiento().getEstado() == EstadoAsignacion.PLANIFICADO) {
            AsignacionTratamiento asignacion = guardada.getAsignacionTratamiento();
            asignacion.setEstado(EstadoAsignacion.EN_EJECUCION);
            asignacionRepository.save(asignacion);
        }

        return mapToResponse(guardada);
    }

    @Transactional
    public TareaResponse update(Integer id, TareaRequest req) {
        Tarea existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        updateEntityFromRequest(existente, req);
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
    public List<TareaResponse> findPorAsignacion(Long idAsignacion) {
        return repository.findByAsignacionTratamiento_IdAsignacion(idAsignacion).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaResponse> findParaLiquidacion(Integer idEmpleado, LocalDate inicio, LocalDate fin) {
        Empleado e = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return repository.findByEmpleadoAndFechaBetween(e, inicio, fin).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    private void updateEntityFromRequest(Tarea entidad, TareaRequest req) {
        entidad.setDescripcion(req.getDescripcion());
        entidad.setObservaciones(req.getObservaciones());
        entidad.setHoras(req.getHoras());
        entidad.setFecha(req.getFecha());

        if (req.getIdEmpleado() != null) {
            entidad.setEmpleado(empleadoRepository.findById(req.getIdEmpleado()).orElse(null));
        } else {
            entidad.setEmpleado(null);
        }

        if (req.getIdEstado() != null) {
            entidad.setEstado(estadoRepository.findById(req.getIdEstado()).orElse(null));
        } else {
            entidad.setEstado(null);
        }

        if (req.getIdCatalogoTarea() != null) {
            entidad.setCatalogoTarea(catalogoRepository.findById(req.getIdCatalogoTarea()).orElse(null));
        } else {
            entidad.setCatalogoTarea(null);
        }

        if (req.getIdAsignacion() != null) {
            entidad.setAsignacionTratamiento(asignacionRepository.findById(Long.valueOf(req.getIdAsignacion())).orElse(null));
        } else {
            entidad.setAsignacionTratamiento(null);
        }
    }

    private TareaResponse mapToResponse(Tarea t) {
        TareaResponse res = new TareaResponse();
        res.setIdTarea(t.getIdTarea());
        res.setDescripcion(t.getDescripcion());
        res.setHoras(t.getHoras());
        res.setFecha(t.getFecha());
        res.setObservaciones(t.getObservaciones());

        if (t.getAsignacionTratamiento() != null) {
            res.setIdAsignacion(Math.toIntExact(t.getAsignacionTratamiento().getIdAsignacion()));
        }
        if (t.getEmpleado() != null) {
            res.setIdEmpleado(t.getEmpleado().getIdEmpleado());
            res.setNombreEmpleado(t.getEmpleado().getNombre());
        }
        if (t.getEstado() != null) {
            res.setIdEstado(t.getEstado().getIdEstado());
            res.setNombreEstado(t.getEstado().getNombre());
        }
        if (t.getCatalogoTarea() != null) {
            res.setIdCatalogoTarea(t.getCatalogoTarea().getIdCatalogoTarea());
            res.setNombreTareaCatalogo(t.getCatalogoTarea().getNombre());
        }

        return res;
    }
}
