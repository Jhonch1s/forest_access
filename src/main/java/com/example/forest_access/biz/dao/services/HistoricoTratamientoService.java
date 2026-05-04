package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Cuadrilla;
import com.example.forest_access.biz.dao.entities.HistoricoTratamiento;
import com.example.forest_access.biz.dao.entities.Parcela;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.CuadrillaRepository;
import com.example.forest_access.biz.dao.repositories.HistoricoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.ParcelaRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
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

    public List<HistoricoTratamiento> findAll() {
        return historyRepo.findAll();
    }

    @Transactional
    public HistoricoTratamiento create(HistoricoTratamiento historico) {
        Parcela parcela = parcelaRepository.findById(historico.getParcela().getIdParcela())
                .orElseThrow(() -> new RuntimeException("Parcela no encontrada con id: " +
                        historico.getParcela().getIdParcela()));
        Tratamiento tratamiento = tratamientoRepository.findById(historico.getTratamiento().getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                        historico.getTratamiento().getIdTratamiento()));
        Cuadrilla cuadrilla = cuadrillaRepository.findById(historico.getCuadrilla().getIdCuadrilla())
                .orElseThrow(() -> new RuntimeException("Cuadrilla no encontrada con id: " +
                        historico.getCuadrilla().getIdCuadrilla()));

        historico.setParcela(parcela);
        historico.setTratamiento(tratamiento);
        historico.setCuadrilla(cuadrilla);

        return historyRepo.save(historico);
    }


    @Transactional
    public void delete(Integer idHistorico) {
        if (!historyRepo.existsById(idHistorico)) {
            throw new RuntimeException("HistoricoTratamiento no encontrado con id: " + idHistorico);
        }
        historyRepo.deleteById(idHistorico);
    }

}
