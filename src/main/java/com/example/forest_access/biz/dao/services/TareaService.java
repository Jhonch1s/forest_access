package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Estado;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.entities.Tarea;
import com.example.forest_access.biz.dao.repositories.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TareaService {

    private final TareaRepository repository;

    @Transactional(readOnly = true)
    public List<Tarea> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarea findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + id));
    }

    @Transactional
    public Tarea create(Tarea tarea) {
        // Aseguramos fecha de creación si no viene
        if (tarea.getFechaCreacion() == null) {
            tarea.setFechaCreacion(LocalDate.now());
        }
        return repository.save(tarea);
    }

    @Transactional
    public Tarea update(Integer id, Tarea datos) {
        Tarea existente = findById(id);

        // Actualización de relaciones
        existente.setCatalogoTarea(datos.getCatalogoTarea());
        existente.setEstado(datos.getEstado());
        existente.setEmpleado(datos.getEmpleado());
        existente.setHistoricoTratamiento(datos.getHistoricoTratamiento());
        existente.setPlantilla(datos.getPlantilla());

        // Actualización de fechas y datos operativos
        existente.setFechaInicio(datos.getFechaInicio());
        existente.setFechaFinEstimada(datos.getFechaFinEstimada());
        existente.setFechaFinalizacion(datos.getFechaFinalizacion());
        existente.setHoras(datos.getHoras());
        existente.setDescripcion(datos.getDescripcion());
        existente.setObservaciones(datos.getObservaciones());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        Tarea existente = findById(id);
        repository.delete(existente);
    }


    @Transactional(readOnly = true)
    public List<Tarea> findPorEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado);
    }

    @Transactional(readOnly = true)
    public List<Tarea> findPorEstado(String nombreEstado) {
        return repository.findByEstado_Nombre(nombreEstado);
    }

    @Transactional(readOnly = true)
    public List<Tarea> findPendientesDeEmpleado(Empleado empleado) {
        // Filtra tareas que no están terminadas ("PENDIENTE", "EN_CURSO")
        return repository.findByEmpleadoAndEstado_NombreIn(empleado, List.of("PENDIENTE", "EN_CURSO"));
    }

    @Transactional(readOnly = true)
    public List<Tarea> findPorHistorico(Integer idHistorico) {
        return repository.findByHistoricoTratamiento_IdHistorico(idHistorico);
    }

    @Transactional(readOnly = true)
    public List<Tarea> findParaLiquidacion(Empleado empleado, LocalDate inicio, LocalDate fin) {
        return repository.findByEmpleadoAndFechaFinalizacionBetween(empleado, inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<Tarea> findPorParcela(Integer idParcela) {
        return repository.findByHistoricoTratamiento_Parcela_IdParcela(idParcela);
    }
}