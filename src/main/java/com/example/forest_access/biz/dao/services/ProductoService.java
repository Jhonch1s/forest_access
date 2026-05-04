package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional
    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto update(Integer idProducto, Producto productoActualizado) {
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setContenido(productoActualizado.getContenido());
        productoExistente.setConcentracion(productoActualizado.getConcentracion());
        productoExistente.setUnidadBase(productoActualizado.getUnidadBase());

        return productoRepository.save(productoExistente);
    }

    @Transactional
    public void delete(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException("Producto no encontrado con id: " + idProducto);
        }
        productoRepository.deleteById(idProducto);
    }


}
