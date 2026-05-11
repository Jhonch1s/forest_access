package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.TareaDependenciaResponse;
import com.example.forest_access.biz.dao.entities.TareaDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TareaDependenciaId;
import com.example.forest_access.biz.dao.repositories.TareaDependenciaRepository;
import com.example.forest_access.biz.dao.repositories.TareaRepository;
import com.example.forest_access.dto.TareaDependenciaDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TareaDependenciaService {

    private TareaDependenciaRepository repotarea;
    private TareaRepository tareaRepository;

    public List<TareaDependenciaResponse> mostrarTareaDependencias() {

        return repotarea.findAll().stream().map( td-> {
            TareaDependenciaResponse tdr = new TareaDependenciaResponse();
            tdr.setTareaAnterior(td.getTareaAnterior().getDescripcion());
            tdr.setTareaPosterior(td.getTareaPosterior().getDescripcion());
            tdr.setDiasEsperaMinimo(td.getDiasEsperaMinimo());
            return tdr;
        }).toList();
    }

    public TareaDependencia crearTareaDependencia(TareaDependenciaDTO tareaDependencia) {
        if(Objects.equals(tareaDependencia.getIdTareaPosterior(), tareaDependencia.getIdTareaAnterior())){
            throw new IllegalArgumentException("La misma tarea no puede preceder la misma tarea");
        }
        TareaDependencia td = new TareaDependencia();
        td.setTareaAnterior(tareaRepository.findById(tareaDependencia.getIdTareaAnterior())
                .orElseThrow(() -> new IllegalArgumentException("La tarea anterior no existe")));
        td.setTareaPosterior(tareaRepository.findById(tareaDependencia.getIdTareaPosterior())
                .orElseThrow(() -> new IllegalArgumentException("La tarea posterior no existe")));
        td.setDiasEsperaMinimo(tareaDependencia.getDiasEsperaMinimo());
        return repotarea.save(td);

    }

    public TareaDependenciaResponse deleteTareaDependencia(TareaDependenciaId tareaDependencia) {
        repotarea.deleteById(tareaDependencia);
        TareaDependencia td = repotarea.findById(tareaDependencia)
                .orElseThrow(() -> new IllegalArgumentException("La tarea posterior no existe"));
        TareaDependenciaResponse tdr = new TareaDependenciaResponse();
        tdr.setTareaAnterior(td.getTareaAnterior().getDescripcion());
        tdr.setTareaPosterior(td.getTareaPosterior().getDescripcion());
        tdr.setDiasEsperaMinimo(td.getDiasEsperaMinimo());
        repotarea.delete(td);
        return tdr;
    }
}
