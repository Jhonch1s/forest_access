package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Liquidacion;
import com.example.forest_access.biz.dao.repositories.LiquidacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LiquidacionService {

    private final LiquidacionRepository repository;

    @Transactional(readOnly = true)
    public List<Liquidacion> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Liquidacion findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Liquidación no encontrada con ID: " + id));
    }

    @Transactional
    public Liquidacion create(Liquidacion liquidacion) {
        // Validar que no exista ya una liquidación para este empleado en este periodo exacto
        boolean existe = repository.existsByEmpleadoAndPeriodoInicioAndPeriodoFin(
                liquidacion.getEmpleado(),
                liquidacion.getPeriodoInicio(),
                liquidacion.getPeriodoFin()
        );

        if (existe) {
            throw new IllegalArgumentException("Ya existe una liquidación procesada para este empleado en el período seleccionado.");
        }

        // Aquí agregamos lógica de cálculo automática antes de guardar si fuera necesario
        return repository.save(liquidacion);
    }

    @Transactional
    public Liquidacion update(Integer id, Liquidacion datos) {
        Liquidacion existente = findById(id);

        // Actualizamos los campos
        existente.setPeriodoInicio(datos.getPeriodoInicio());
        existente.setPeriodoFin(datos.getPeriodoFin());
        existente.setTotalJornales(datos.getTotalJornales());
        existente.setValorJornal(datos.getValorJornal());
        existente.setTotalNominal(datos.getTotalNominal());
        existente.setTotalProduccion(datos.getTotalProduccion());
        existente.setTotalIncentivo(datos.getTotalIncentivo());
        existente.setAdelantos(datos.getAdelantos());
        existente.setTotalFinal(datos.getTotalFinal());
        existente.setObservaciones(datos.getObservaciones());

        return repository.save(existente);
    }

    @Transactional
    public void delete(Integer id) {
        Liquidacion existente = findById(id);
        repository.delete(existente);
    }

    @Transactional
    public List<Liquidacion> findByEmpleado(Integer idEmpleado) {
        return repository.findByEmpleado_IdEmpleado(idEmpleado);
    }

    @Transactional
    public List<Liquidacion> findByPeriodo(LocalDate desde, LocalDate hasta) {
        return repository.findByPeriodoInicioBetween(desde, hasta);
    }

    @Transactional
    public List<Liquidacion> findHistorialEmpleado(Empleado empleado) {
        return repository.findByEmpleadoOrderByPeriodoInicioDesc(empleado);
    }
}