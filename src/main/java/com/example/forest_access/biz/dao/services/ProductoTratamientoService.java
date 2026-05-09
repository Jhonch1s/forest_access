package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import com.example.forest_access.biz.dao.repositories.ProductoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import com.example.forest_access.dto.ProductoTratamientoDTO;
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

    public List<ProductoTratamientoDTO> findAll()
    {
        return prodRepo.findAll().stream().map( PT ->{
            ProductoTratamientoDTO prodTraDTO = new ProductoTratamientoDTO();
            prodTraDTO.setIdProducto(PT.getProducto().getIdProducto());
            prodTraDTO.setIdTratamiento(PT.getTratamiento().getIdTratamiento());
            prodTraDTO.setDosis(PT.getDosis());
            prodTraDTO.setUnidad(PT.getUnidad());
            return prodTraDTO;
        }).toList();
    }

    @Transactional
    public ProductoTratamiento create(ProductoTratamientoDTO productoTratamiento) {
        Tratamiento tratamiento = tratamientoRepository.findById(productoTratamiento.getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                        productoTratamiento.getIdTratamiento()));
        Producto producto = productoRepository.findById(productoTratamiento.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " +
                        productoTratamiento.getIdProducto()));
        ProductoTratamiento prodTrat =  new ProductoTratamiento();
        prodTrat.setTratamiento(tratamiento);
        prodTrat.setProducto(producto);
        prodTrat.setDosis(productoTratamiento.getDosis());
        prodTrat.setUnidad(productoTratamiento.getUnidad());

        return prodRepo.save(prodTrat);
    }

    @Transactional
    public ProductoTratamientoDTO update(Integer idProductoTratamiento, ProductoTratamientoDTO productoTratamientoActualizado) {
        ProductoTratamiento existente = prodRepo.findById(idProductoTratamiento)
                .orElseThrow(() -> new RuntimeException("ProductoTratamiento no encontrado con id: " + idProductoTratamiento));

        existente.setDosis(productoTratamientoActualizado.getDosis());
        existente.setUnidad(productoTratamientoActualizado.getUnidad());

        if (productoTratamientoActualizado.getIdTratamiento() != null) {
            Tratamiento tratamiento = tratamientoRepository.findById(productoTratamientoActualizado.getIdTratamiento())
                    .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " +
                            productoTratamientoActualizado.getIdTratamiento()));
            existente.setTratamiento(tratamiento);
        }

        if (productoTratamientoActualizado.getIdProducto() != null) {
            Producto producto = productoRepository.findById(productoTratamientoActualizado.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " +
                            productoTratamientoActualizado.getIdProducto()));
            existente.setProducto(producto);
        }

        prodRepo.save(existente);
        return productoTratamientoActualizado;
    }

    @Transactional
    public ProductoTratamientoDTO delete(Integer idProductoTratamiento) {
        if (!prodRepo.existsById(idProductoTratamiento)) {
            throw new RuntimeException("ProductoTratamiento no encontrado con id: " + idProductoTratamiento);
        }
        ProductoTratamiento prod = prodRepo.findById(idProductoTratamiento)
                        .orElseThrow( ()->new RuntimeException("ProductoTratamiento no encotrado"));
        ProductoTratamientoDTO prodTraDTO = new ProductoTratamientoDTO();
        prodTraDTO.setIdProducto(prod.getProducto().getIdProducto());
        prodTraDTO.setIdTratamiento(prod.getTratamiento().getIdTratamiento());
        prodTraDTO.setDosis(prod.getDosis());
        prodTraDTO.setUnidad(prod.getUnidad());
        prodRepo.deleteById(idProductoTratamiento);
        return prodTraDTO;
    }
}
