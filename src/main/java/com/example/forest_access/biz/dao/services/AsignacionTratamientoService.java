package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.request.AsignacionTratamientoRequest;
import com.example.forest_access.api.controllers.response.AsignacionTratamientoPaginado;
import com.example.forest_access.api.controllers.response.AsignacionTratamientoResponse;
import com.example.forest_access.biz.dao.entities.*;
import com.example.forest_access.biz.dao.repositories.*;
import com.example.forest_access.enums.EstadoAsignacion;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionTratamientoService {
    private final AsignacionTratamientoRepository asignacionRepo;
    private final ParcelaRepository parcelaRepo;
    private final RodalRepository rodalRepo;
    private final TratamientoRepository tratamientoRepo;
    private final TratamientoDependenciaRepository dependenciaRepo;

    public List<AsignacionTratamientoResponse> getAll() {
        return asignacionRepo.findAll().stream().map(this::toResponse).toList();
    }

    public List<AsignacionTratamientoResponse> getByParcela(Long idParcela) {
        return asignacionRepo.findByParcelaIdParcela(idParcela).stream().map(this::toResponse).toList();
    }

    public AsignacionTratamientoPaginado getByParcelaPaginado(Long idParcela, Integer offset,Integer limite){

        List<AsignacionTratamientoResponse> todasAsignaciones = asignacionRepo.findAll().stream()
                .skip(offset)
                .limit(limite)
                .map(this::toResponse).toList();

        List<AsignacionTratamientoResponse> asignaciones = asignacionRepo.findByParcelaIdParcela(idParcela).stream()
                .skip(offset)
                .limit(limite)
                .map(this::toResponse).toList();
        AsignacionTratamientoPaginado atp = new AsignacionTratamientoPaginado();
        if(idParcela == 0){
            atp.setAsignaciones(todasAsignaciones);
            atp.setTotal(asignacionRepo.findAll().size());
            atp.setPagina(offset);
            atp.setLimite(limite);
        }else{
            atp.setAsignaciones(asignaciones);
            atp.setTotal(asignacionRepo.findByParcelaIdParcela(idParcela).size());
            atp.setPagina(offset);
            atp.setLimite(limite);
        }

        return atp;
    }

    public List<AsignacionTratamientoResponse> getByRodal(Long idRodal) {
        return asignacionRepo.findByParcelaRodalIdRodal(idRodal).stream().map(this::toResponse).toList();
    }

    @Transactional
    public List<AsignacionTratamientoResponse> create(AsignacionTratamientoRequest request) {
        List<Parcela> parcelas = new ArrayList<>();

        if (request.getIdRodal() != null) {
            parcelas = parcelaRepo.findByRodal_IdRodal(Math.toIntExact(request.getIdRodal()));
        } else if (request.getIdParcela() != null) {
            parcelas.add(parcelaRepo.findById(Math.toIntExact(request.getIdParcela()))
                    .orElseThrow(() -> new RuntimeException("Parcela no encontrada")));
        }

        Tratamiento tratamiento = tratamientoRepo.findById(Math.toIntExact(request.getIdTratamiento()))
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        List<AsignacionTratamiento> creadas = new ArrayList<>();

        for (Parcela parcela : parcelas) {
            // Validación: no duplicados
            if (asignacionRepo.existsByParcelaIdParcelaAndTratamientoIdTratamientoAndEstadoNot(
                    Long.valueOf(parcela.getIdParcela()),
                    Long.valueOf(tratamiento.getIdTratamiento()),
                    EstadoAsignacion.CANCELADO)) {
                continue;
            }

            // Validación: dependencias
            validarDependencias(parcela, tratamiento, request.getFechaInicioEstimada());

            AsignacionTratamiento asignacion = AsignacionTratamiento.builder()
                    .parcela(parcela)
                    .tratamiento(tratamiento)
                    .fechaAsignacion(LocalDate.parse(request.getFechaAsignacion()))
                    .fechaInicioEstimada(LocalDate.parse(request.getFechaInicioEstimada()))
                    .fechaFinEstimada(LocalDate.parse(request.getFechaFinEstimada()))
                    .observaciones(request.getObservaciones())
                    .estado(request.getEstado() != null ? request.getEstado() : EstadoAsignacion.PENDIENTE)
                    .build();

            creadas.add(asignacionRepo.save(asignacion));
        }

        return creadas.stream().map(this::toResponse).toList();
    }

    private void validarDependencias(Parcela parcela, Tratamiento tratamiento, String fechaPropuesta) {
        // Buscar si este tratamiento tiene prerequisitos
        List<TratamientoDependencia> prerequisitos = dependenciaRepo
                .findByTratamientoPosteriorIdTratamiento(tratamiento.getIdTratamiento());

        for (TratamientoDependencia dep : prerequisitos) {
            Integer idTratamientoAnterior = dep.getTratamientoAnterior().getIdTratamiento();
            int diasMinimos = dep.getDiasEsperaMinimo();

            // Verificar si el tratamiento anterior fue completado en esta parcela
            LocalDate fechaEjecucionAnterior = buscarFechaCompletado(parcela.getIdParcela(), idTratamientoAnterior);

            if (fechaEjecucionAnterior == null) {
                throw new RuntimeException("Prerequisito no cumplido: debe aplicarse "
                        + dep.getTratamientoAnterior().getNombre() + " primero");
            }

            LocalDate fechaInicio = LocalDate.parse(fechaPropuesta);
            long diasDiferencia = ChronoUnit.DAYS.between(fechaEjecucionAnterior, fechaInicio);

            if (diasDiferencia < diasMinimos) {
                throw new RuntimeException("Deben pasar al menos " + diasMinimos
                        + " días desde " + dep.getTratamientoAnterior().getNombre());
            }
        }
    }

    private LocalDate buscarFechaCompletado(Integer idParcela, Integer idTratamiento) {
        Optional<AsignacionTratamiento> asignacion = asignacionRepo
                .findTopByParcelaIdParcelaAndTratamientoIdTratamientoAndEstadoOrderByFechaFinEstimadaDesc(
                        idParcela, idTratamiento, EstadoAsignacion.COMPLETADO);

        return asignacion.map(AsignacionTratamiento::getFechaFinEstimada).orElse(null);
    }

    @Transactional
    public AsignacionTratamientoResponse update(Long id, AsignacionTratamientoRequest request) {
        AsignacionTratamiento asignacion = asignacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con id: " + id));

        if (request.getFechaAsignacion() != null) {
            asignacion.setFechaAsignacion(LocalDate.parse(request.getFechaAsignacion()));
        }
        if (request.getFechaInicioEstimada() != null) {
            asignacion.setFechaInicioEstimada(LocalDate.parse(request.getFechaInicioEstimada()));
        }
        if (request.getFechaFinEstimada() != null) {
            asignacion.setFechaFinEstimada(LocalDate.parse(request.getFechaFinEstimada()));
        }
        if (request.getObservaciones() != null) {
            asignacion.setObservaciones(request.getObservaciones());
        }
        if (request.getEstado() != null) {
            asignacion.setEstado(request.getEstado());
        }

        return toResponse(asignacionRepo.save(asignacion));
    }

    @Transactional
    public AsignacionTratamientoResponse delete(Long id) {
        AsignacionTratamiento asignacion = asignacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con id: " + id));
        AsignacionTratamientoResponse response = toResponse(asignacion);
        asignacionRepo.deleteById(id);
        return response;
    }

    @Transactional
    public AsignacionTratamientoResponse iniciarEjecucion(Long id) {
        AsignacionTratamiento asignacion = asignacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con id: " + id));
        asignacion.setEstado(EstadoAsignacion.EN_EJECUCION);
        return toResponse(asignacionRepo.save(asignacion));
    }

    private AsignacionTratamientoResponse toResponse(AsignacionTratamiento a) {
        return AsignacionTratamientoResponse.builder()
                .idAsignacion(a.getIdAsignacion())
                .idParcela(a.getParcela().getIdParcela() != null ? Long.valueOf(a.getParcela().getIdParcela()) : null)
                .nombreParcela(a.getParcela().getNombre())
                .idRodal(a.getParcela().getRodal() != null ? Long.valueOf(a.getParcela().getRodal().getIdRodal()) : null)
                .nombreRodal(a.getParcela().getRodal() != null ? a.getParcela().getRodal().getNombre() : null)
                .idCampo(a.getParcela().getRodal() != null && a.getParcela().getRodal().getCampo() != null
                        ? Long.valueOf(a.getParcela().getRodal().getCampo().getIdCampo()) : null)
                .nombreCampo(a.getParcela().getRodal() != null && a.getParcela().getRodal().getCampo() != null
                        ? a.getParcela().getRodal().getCampo().getNombre() : null)
                .idTratamiento(Long.valueOf(a.getTratamiento().getIdTratamiento()))
                .nombreTratamiento(a.getTratamiento().getNombre())
                .fechaAsignacion(a.getFechaAsignacion() != null ? a.getFechaAsignacion().toString() : null)
                .fechaInicioEstimada(a.getFechaInicioEstimada() != null ? a.getFechaInicioEstimada().toString() : null)
                .fechaFinEstimada(a.getFechaFinEstimada() != null ? a.getFechaFinEstimada().toString() : null)
                .observaciones(a.getObservaciones())
                .estado(a.getEstado() != null ? a.getEstado().name() : null)
                .build();
    }
}
