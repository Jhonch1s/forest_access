package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.LiquidacionResponse;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Liquidacion;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.LiquidacionRepository;
import com.example.forest_access.dto.LiquidacionDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LiquidacionService {

    private final LiquidacionRepository repository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<LiquidacionResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LiquidacionResponse findById(Integer id) {
        Liquidacion liq = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Liquidación no encontrada con ID: " + id));
        return mapToResponse(liq);
    }

    @Transactional
    public LiquidacionResponse create(LiquidacionDTO dto) {
        Empleado empleado = empleadoRepository.findById(dto.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (repository.existsByEmpleadoAndPeriodoInicioAndPeriodoFin(empleado, dto.getPeriodoInicio(), dto.getPeriodoFin())) {
            throw new IllegalArgumentException("Ya existe una liquidación para este empleado en este período.");
        }

        Liquidacion nueva = new Liquidacion();
        nueva.setEmpleado(empleado);
        nueva.setPeriodoInicio(dto.getPeriodoInicio());
        nueva.setPeriodoFin(dto.getPeriodoFin());
        nueva.setObservaciones(dto.getObservaciones());

        nueva.setTotalJornales(dto.getTotalJornales());
        nueva.setValorJornal(dto.getValorJornal());
        nueva.setTotalNominal(dto.getTotalNominal());
        nueva.setTotalProduccion(dto.getTotalProduccion());
        nueva.setTotalIncentivo(dto.getTotalIncentivo());
        nueva.setAdelantos(dto.getAdelantos());
        nueva.setTotalFinal(dto.getTotalFinal());

        return mapToResponse(repository.save(nueva));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("No existe");
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LiquidacionResponse> findByEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LiquidacionResponse mapToResponse(Liquidacion liq) {
        LiquidacionResponse res = new LiquidacionResponse();
        res.setIdLiquidacion(liq.getIdLiquidacion());
        res.setNombreEmpleado(liq.getEmpleado().getNombre());
        res.setCedulaEmpleado(liq.getEmpleado().getCedula());
        res.setPeriodo(liq.getPeriodoInicio() + " al " + liq.getPeriodoFin());
        res.setTotalFinal(liq.getTotalFinal());
        res.setObservaciones(liq.getObservaciones());
        return res;
    }
}