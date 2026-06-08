package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.TareaRequest;
import com.example.forest_access.api.controllers.response.TareaResponse;
import com.example.forest_access.biz.dao.entities.*;
import com.example.forest_access.biz.dao.repositories.*;
import com.example.forest_access.dto.ReporteEmpleadoDTO;
import com.example.forest_access.dto.ReporteHabilitacionDTO;
import com.example.forest_access.dto.ReporteTareaDTO;
import com.example.forest_access.enums.EstadoAsignacion;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TareaService {

    private final TareaRepository repository;
    private final EmpleadoRepository empleadoRepository;
    private final EstadoRepository estadoRepository;
    private final CatalogoTareaRepository catalogoRepository;
    private final AsignacionTratamientoRepository asignacionRepository;
    private final EmpleadoHabilitacionRepository empleadoHabilitacionRepository;

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

    @Transactional(readOnly = true)
    public List<ReporteEmpleadoDTO> getReporteBatch(LocalDate inicio, LocalDate hasta) {
        List<Object[]> empleadosData = empleadoRepository.findAllActiveWithCategoria();
        List<Tarea> tareasData = repository.findByFechaBetween(inicio, hasta);
        List<EmpleadoHabilitacion> habsData = empleadoHabilitacionRepository.findAll();

        Map<Integer, ReporteEmpleadoDTO> reporteMap = new LinkedHashMap<>();

        for (Object[] row : empleadosData) {
            Integer id = ((Number) row[0]).intValue();
            ReporteEmpleadoDTO dto = new ReporteEmpleadoDTO();
            dto.setIdEmpleado(id);
            dto.setNombre((String) row[1]);
            dto.setCedula((String) row[2]);
            dto.setNombreCategoria((String) row[3]);
            dto.setValorJornal(row[4] != null ? (BigDecimal) row[4] : BigDecimal.ZERO);
            dto.setTotalHoras(BigDecimal.ZERO);
            dto.setTotalTareas(0);
            dto.setTareas(new ArrayList<>());
            dto.setHabilitaciones(new ArrayList<>());
            reporteMap.put(id, dto);
        }

        // Agrupar tareas por idEmpleado y luego por nombre de catálogo
        Map<Integer, Map<String, List<Tarea>>> tareasAgrupadas = tareasData.stream()
                .filter(t -> t.getEmpleado() != null && t.getCatalogoTarea() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getEmpleado().getIdEmpleado(),
                        Collectors.groupingBy(t -> t.getCatalogoTarea().getNombre())
                ));

        // Calcular días trabajados distintos por empleado
        Map<Integer, Long> diasTrabajadosMap = tareasData.stream()
                .filter(t -> t.getEmpleado() != null && t.getFecha() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getEmpleado().getIdEmpleado(),
                        Collectors.mapping(Tarea::getFecha, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        // Llenar tareas por catálogo para cada empleado
        tareasAgrupadas.forEach((idEmpleado, catalogoMap) -> {
            ReporteEmpleadoDTO dto = reporteMap.get(idEmpleado);
            if (dto != null) {
                catalogoMap.forEach((nombreCatalogo, listaTareas) -> {
                    ReporteTareaDTO rtd = new ReporteTareaDTO();
                    rtd.setNombreCatalogo(nombreCatalogo);
                    rtd.setCantidad(listaTareas.size());

                    BigDecimal horasTotal = listaTareas.stream()
                            .map(Tarea::getHoras)
                            .filter(h -> h != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    rtd.setHoras(horasTotal);

                    dto.getTareas().add(rtd);
                    dto.setTotalTareas(dto.getTotalTareas() + rtd.getCantidad());
                    dto.setTotalHoras(dto.getTotalHoras().add(rtd.getHoras()));
                });
            }
        });

        // Llenar días trabajados para cada empleado
        diasTrabajadosMap.forEach((idEmpleado, dias) -> {
            ReporteEmpleadoDTO dto = reporteMap.get(idEmpleado);
            if (dto != null) {
                dto.setDiasTrabajados(dias.intValue());
            }
        });

        for (EmpleadoHabilitacion eh : habsData) {
            Integer idEmpleado = eh.getEmpleado().getIdEmpleado();
            ReporteEmpleadoDTO dto = reporteMap.get(idEmpleado);
            if (dto == null) continue;

            ReporteHabilitacionDTO hab = new ReporteHabilitacionDTO();
            hab.setNombreHabilitacion(eh.getHabilitacion().getNombre());
            hab.setFechaVencimiento(eh.getFechaVencimiento());
            dto.getHabilitaciones().add(hab);
        }

        return new ArrayList<>(reporteMap.values());
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
