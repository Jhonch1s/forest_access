package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.repositories.CategoriaEmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaEmpleadoService {

    private final CategoriaEmpleadoRepository repository;

    public CategoriaEmpleadoService(CategoriaEmpleadoRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaEmpleado> findAll() {
        return repository.findAll();
    }

    public CategoriaEmpleado findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoría no encontrada con id: " + id
                ));
    }

    public CategoriaEmpleado create(CategoriaEmpleado categoria) {
        if (repository.findByNombre(categoria.getNombre()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con el nombre: " + categoria.getNombre()
            );
        }
        return repository.save(categoria);
    }

    public CategoriaEmpleado update(Integer id, CategoriaEmpleado datos) {
        CategoriaEmpleado existente = findById(id);

        // si el nombre cambió, verificar que el nuevo no esté tomado
        if (!existente.getNombre().equalsIgnoreCase(datos.getNombre())) {
            if (repository.findByNombre(datos.getNombre()).isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe una categoría con el nombre: " + datos.getNombre()
                );
            }
        }

        existente.setNombre(datos.getNombre());
        existente.setValorJornal(datos.getValorJornal());
        existente.setDescripcion(datos.getDescripcion());

        return repository.save(existente);
    }

    public void delete(Integer id) {
        CategoriaEmpleado existente = findById(id);
        repository.delete(existente);
    }
}