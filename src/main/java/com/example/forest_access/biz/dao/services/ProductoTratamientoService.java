package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import com.example.forest_access.biz.dao.repositories.ProductoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoTratamientoService {

    private ProductoTratamientoRepository prodRepo;
    private TratamientoRepository tratamientoRepository;
    private ProductoRepository productoRepository;

    public List<ProductoTratamiento> findAll() {
        return prodRepo.findAll();
    }

    @Transactional
    public ProductoTratamiento create(ProductoTratamiento productoTratamiento) {
        Tratamiento tratamiento = tratamientoRepository.findById(productoTratamiento.getTratamiento().getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                        productoTratamiento.getTratamiento().getIdTratamiento()));
        Producto producto = productoRepository.findById(productoTratamiento.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " +
                        productoTratamiento.getProducto().getIdProducto()));

        productoTratamiento.setTratamiento(tratamiento);
        productoTratamiento.setProducto(producto);

        return prodRepo.save(productoTratamiento);
    }

    @Transactional
    public ProductoTratamiento update(Integer idProductoTratamiento, ProductoTratamiento productoTratamientoActualizado) {
        ProductoTratamiento existente = prodRepo.findById(idProductoTratamiento)
                .orElseThrow(() -> new RuntimeException("ProductoTratamiento no encontrado con id: " + idProductoTratamiento));

        existente.setDosis(productoTratamientoActualizado.getDosis());
        existente.setUnidad(productoTratamientoActualizado.getUnidad());

        if (productoTratamientoActualizado.getTratamiento() != null &&
                productoTratamientoActualizado.getTratamiento().getIdTratamiento() != null) {
            Tratamiento tratamiento = tratamientoRepository.findById(productoTratamientoActualizado.getTratamiento().getIdTratamiento())
                    .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                            productoTratamientoActualizado.getTratamiento().getIdTratamiento()));
            existente.setTratamiento(tratamiento);
        }

        if (productoTratamientoActualizado.getProducto() != null &&
                productoTratamientoActualizado.getProducto().getIdProducto() != null) {
            Producto producto = productoRepository.findById(productoTratamientoActualizado.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " +
                            productoTratamientoActualizado.getProducto().getIdProducto()));
            existente.setProducto(producto);
        }

        return prodRepo.save(existente);
    }

    @Transactional
    public void delete(Integer idProductoTratamiento) {
        if (!prodRepo.existsById(idProductoTratamiento)) {
            throw new RuntimeException("ProductoTratamiento no encontrado con id: " + idProductoTratamiento);
        }
        prodRepo.deleteById(idProductoTratamiento);
    }
}
