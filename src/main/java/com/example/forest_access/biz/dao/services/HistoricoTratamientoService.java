package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.HistoricoTratamientoResponse;
import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.HistoricoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.ParcelaRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import com.example.forest_access.dto.HistoricoTratamientoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoTratamientoService {

    private HistoricoTratamientoRepository historyRepo;
    private final ParcelaRepository parcelaRepository;
    private final TratamientoRepository tratamientoRepository;
    private final CuadrillaRepository cuadrillaRepository;

    public List<HistoricoTratamientoResponse> findAll() {

        return historyRepo.findAll().stream().map(ht ->{
            HistoricoTratamientoResponse htdto = new HistoricoTratamientoResponse();
            htdto.setNombreParcela(ht.getParcela().getNombre());
            htdto.setNombreTratamiento(ht.getTratamiento().getNombre());
            htdto.setNombreCuadrilla(ht.getCuadrilla().getNombre());
            htdto.setFechaInicio(ht.getFechaInicio());
            htdto.setFechaFin(ht.getFechaFin());
            htdto.setObservaciones(ht.getObservaciones());
            return htdto;
        }).toList();
    }

    @Transactional
    public HistoricoTratamiento create(HistoricoTratamientoDTO historico) {
        Parcela parcela = parcelaRepository.findById(historico.getIdParcela())
                .orElseThrow(() -> new RuntimeException("Parcela no encontrada con id: " +
                        historico.getIdParcela()));
        Tratamiento tratamiento = tratamientoRepository.findById(historico.getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                        historico.getIdTratamiento()));
        Cuadrilla cuadrilla = cuadrillaRepository.findById(historico.getCuadrilla())
                .orElseThrow(() -> new RuntimeException("Cuadrilla no encontrada con id: " +
                        historico.getCuadrilla()));

        HistoricoTratamiento ht = new HistoricoTratamiento();
        ht.setParcela(parcela);
        ht.setTratamiento(tratamiento);
        ht.setCuadrilla(cuadrilla);
        ht.setFechaInicio(historico.getFechaInicio());
        ht.setFechaFin(historico.getFechaFin());
        ht.setObservaciones(historico.getObservaciones());

        return historyRepo.save(ht);
    }


    @Transactional
    public HistoricoTratamientoResponse delete(Integer idHistorico) {
        if (!historyRepo.existsById(idHistorico)) {
            throw new RuntimeException("HistoricoTratamiento no encontrado con id: " + idHistorico);
        }
        HistoricoTratamiento ht = historyRepo.findById(idHistorico)
                        .orElseThrow(()->new RuntimeException("HistoricoTratamiento no encontrado con id: " + idHistorico));
        HistoricoTratamientoResponse htr = new HistoricoTratamientoResponse();
        htr.setNombreParcela(ht.getParcela().getNombre());
        htr.setNombreTratamiento(ht.getTratamiento().getNombre());
        htr.setNombreCuadrilla(ht.getCuadrilla().getNombre());
        htr.setFechaInicio(ht.getFechaInicio());
        htr.setFechaFin(ht.getFechaFin());
        htr.setObservaciones(ht.getObservaciones());
        historyRepo.deleteById(idHistorico);
        return htr;
    }

}
