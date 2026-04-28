package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Tarea;
import com.example.forest_access.biz.dao.entities.TareaDependencia;
import com.example.forest_access.biz.dao.entities.embeddables.TareaDependenciaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaDependenciaRepository
        extends JpaRepository<TareaDependencia, TareaDependenciaId> {

    // qué tareas debo esperar antes de iniciar esta
    List<TareaDependencia> findByTareaPosterior(Tarea tarea);
    List<TareaDependencia> findByTareaPosterior_IdTarea(Integer idTarea);

    // qué tareas bloquea esta al no estar finalizada
    List<TareaDependencia> findByTareaAnterior(Tarea tarea);
    List<TareaDependencia> findByTareaAnterior_IdTarea(Integer idTarea);
}
