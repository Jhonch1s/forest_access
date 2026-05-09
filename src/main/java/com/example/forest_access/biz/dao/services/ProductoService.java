package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import com.example.forest_access.dto.ProductoDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

    private ProductoRepository productoRepository;

    public List<ProductoDTO> findAll() {

        return productoRepository.findAll().stream().map(p ->{
            ProductoDTO producto = new ProductoDTO();
            BeanUtils.copyProperties(p, producto);
            return producto;
        }).toList();
    }

    @Transactional
    public Producto create(ProductoDTO producto) {
        Producto p = new Producto();
        BeanUtils.copyProperties(producto, p);
        productoRepository.save(p);
        return p;
    }

    @Transactional
    public ProductoDTO update(Integer idProducto, ProductoDTO productoActualizado) {
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setContenido(productoActualizado.getContenido());
        productoExistente.setConcentracion(productoActualizado.getConcentracion());
        productoExistente.setUnidadBase(productoActualizado.getUnidadBase());

        productoRepository.save(productoExistente);
        return productoActualizado;
    }

    @Transactional
    public ProductoDTO delete(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                        .orElseThrow(()-> new RuntimeException("No existe el producto"));
        ProductoDTO p  = new ProductoDTO();
        BeanUtils.copyProperties(producto, p);
        productoRepository.delete(producto);
        return p;
    }


}
