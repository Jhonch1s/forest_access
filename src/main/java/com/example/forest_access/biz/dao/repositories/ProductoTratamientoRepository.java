package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoTratamientoRepository
        extends JpaRepository<ProductoTratamiento, Integer> {

    List<ProductoTratamiento> findByTratamiento(Tratamiento tratamiento);
    List<ProductoTratamiento> findByTratamiento_IdTratamiento(Integer idTratamiento);
    List<ProductoTratamiento> findByProducto(Producto producto);
}
