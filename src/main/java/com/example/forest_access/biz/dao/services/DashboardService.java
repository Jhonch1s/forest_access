package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.EmpleadoHabilitacion;
import com.example.forest_access.biz.dao.entities.Tarea;
import com.example.forest_access.biz.dao.entities.TareaAsignada;
import com.example.forest_access.biz.dao.repositories.EmpleadoHabilitacionRepository;
import com.example.forest_access.biz.dao.repositories.TareaAsignadaRepository;
import com.example.forest_access.biz.dao.repositories.TareaRepository;
import com.example.forest_access.dto.dashboard.CuadrillaResumenDTO;
import com.example.forest_access.dto.dashboard.DashboardDTO;
import com.example.forest_access.dto.dashboard.EstadisticasDTO;
import com.example.forest_access.dto.dashboard.HabilitacionResumenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TareaRepository tareaRepository;
    private final TareaAsignadaRepository tareaAsignadaRepository;
    private final EmpleadoHabilitacionRepository empleadoHabilitacionRepository;

    @Transactional(readOnly = true)
    public DashboardDTO obtenerDatosDashboard() {
        LocalDate hoy = LocalDate.now();

        // 1. Cuadrillas Activas
        // Cuadrillas que tengan tareas asignadas cuya fecha limite sea >= hoy
        List<TareaAsignada> asignadasVigentes = tareaAsignadaRepository.findByFechaLimiteGreaterThanEqual(hoy);
        
        List<CuadrillaResumenDTO> cuadrillas = asignadasVigentes.stream()
                .map(ta -> new CuadrillaResumenDTO(
                        ta.getCuadrilla().getIdCuadrilla(),
                        ta.getCuadrilla().getNombre(),
                        ta.getAsignacionTratamiento().getTratamiento().getNombre(),
                        ta.getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                ))
                // Evitar duplicados si una cuadrilla tiene varias tareas asignadas hoy
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(CuadrillaResumenDTO::getId, c -> c, (c1, c2) -> c1),
                        m -> new ArrayList<>(m.values())
                ));

        // 2. Habilitaciones por vencer (en los próximos 7 días) o vencidas
        LocalDate en7Dias = hoy.plusDays(7);
        List<EmpleadoHabilitacion> habs = empleadoHabilitacionRepository.findAll();
        List<HabilitacionResumenDTO> habilitaciones = habs.stream()
                .filter(h -> h.getFechaVencimiento() != null && !h.getFechaVencimiento().isAfter(en7Dias))
                .map(h -> {
                    String estado = h.getFechaVencimiento().isBefore(hoy) ? "Vencida" : "Por vencer";
                    return new HabilitacionResumenDTO(
                            h.getId().getIdHabilitacion() * 1000 + h.getId().getIdEmpleado(), // ID compuesto temporal
                            h.getEmpleado().getNombre(),
                            h.getHabilitacion().getNombre(),
                            h.getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            estado
                    );
                })
                .sorted(Comparator.comparing(h -> h.getEstado().equals("Vencida") ? 0 : 1))
                .collect(Collectors.toList());

        // 3. Estadísticas
        // A) Productividad Semanal (Lunes a Viernes de la semana actual)
        LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<Tarea> tareasEstaSemana = tareaRepository.findByFechaBetween(inicioSemana, finSemana);

        List<Integer> productividad = Arrays.asList(0, 0, 0, 0, 0); // Lunes a Viernes
        for (Tarea t : tareasEstaSemana) {
            int dia = t.getFecha().getDayOfWeek().getValue();
            if (dia >= 1 && dia <= 5) {
                productividad.set(dia - 1, productividad.get(dia - 1) + 1); // Sumar 1 tarea finalizada o registrada
            }
        }

        // B) Evolución Horas (últimas 4 semanas móviles)
        // Semana -3, Semana -2, Semana -1, Semana Actual
        LocalDate inicioSemanaActual = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate inicioSemanaMenos1 = inicioSemanaActual.minusWeeks(1);
        LocalDate inicioSemanaMenos2 = inicioSemanaActual.minusWeeks(2);
        LocalDate inicioSemanaMenos3 = inicioSemanaActual.minusWeeks(3);

        List<Tarea> tareasUltimas4Semanas = tareaRepository.findByFechaBetween(inicioSemanaMenos3, hoy);
        
        BigDecimal horasSemana3 = BigDecimal.ZERO;
        BigDecimal horasSemana2 = BigDecimal.ZERO;
        BigDecimal horasSemana1 = BigDecimal.ZERO;
        BigDecimal horasSemanaActual = BigDecimal.ZERO;

        for (Tarea t : tareasUltimas4Semanas) {
            BigDecimal hrs = t.getHoras() != null ? t.getHoras() : BigDecimal.ZERO;
            LocalDate f = t.getFecha();
            if (!f.isBefore(inicioSemanaActual)) {
                horasSemanaActual = horasSemanaActual.add(hrs);
            } else if (!f.isBefore(inicioSemanaMenos1)) {
                horasSemana1 = horasSemana1.add(hrs);
            } else if (!f.isBefore(inicioSemanaMenos2)) {
                horasSemana2 = horasSemana2.add(hrs);
            } else if (!f.isBefore(inicioSemanaMenos3)) {
                horasSemana3 = horasSemana3.add(hrs);
            }
        }

        List<BigDecimal> evolucionHoras = Arrays.asList(horasSemana3, horasSemana2, horasSemana1, horasSemanaActual);
        List<String> labelsSemanas = Arrays.asList(
                inicioSemanaMenos3.format(DateTimeFormatter.ofPattern("dd/MM")),
                inicioSemanaMenos2.format(DateTimeFormatter.ofPattern("dd/MM")),
                inicioSemanaMenos1.format(DateTimeFormatter.ofPattern("dd/MM")),
                inicioSemanaActual.format(DateTimeFormatter.ofPattern("dd/MM"))
        );

        // C) Estado Tareas (En proceso, Pendiente, Finalizada)
        Map<String, Integer> estadoTareas = new HashMap<>();
        estadoTareas.put("En proceso", 0);
        estadoTareas.put("Pendiente", 0);
        estadoTareas.put("Finalizada", 0);
        
        // Vamos a tomar todas las tareas del mes o activas
        for (Tarea t : tareasUltimas4Semanas) {
            String estadoStr = t.getEstado() != null ? t.getEstado().getNombre() : "Pendiente";
            if (estadoStr.equalsIgnoreCase("En proceso")) estadoStr = "En proceso";
            else if (estadoStr.equalsIgnoreCase("Finalizado") || estadoStr.equalsIgnoreCase("Finalizada")) estadoStr = "Finalizada";
            else estadoStr = "Pendiente";
            
            estadoTareas.put(estadoStr, estadoTareas.getOrDefault(estadoStr, 0) + 1);
        }

        EstadisticasDTO estadisticas = new EstadisticasDTO(productividad, evolucionHoras, labelsSemanas, estadoTareas);

        return new DashboardDTO(cuadrillas, habilitaciones, estadisticas);
    }
}
