package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.TareaDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TareaDependenciaId;
import com.example.forest_access.biz.dao.repositories.TareaDependenciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TareaDependenciaService {

    private TareaDependenciaRepository repotarea;

    public List<TareaDependencia> mostrarTareaDependencias() {
        return repotarea.findAll();
    }

    public TareaDependencia crearTareaDependencia(TareaDependencia tareaDependencia) {
        if(Objects.equals(tareaDependencia.getTareaPosterior().getIdTarea(), tareaDependencia.getTareaAnterior().getIdTarea())){
            throw new IllegalArgumentException("La misma tarea no puede preceder la misma tarea");
        }
        return repotarea.save(tareaDependencia);

    }

    public void deleteTareaDependencia(TareaDependenciaId tareaDependencia) {
        repotarea.deleteById(tareaDependencia);
    }
}
