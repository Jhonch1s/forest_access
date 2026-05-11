package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.ProductoTratamientoResponse;
import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import com.example.forest_access.biz.dao.repositories.ProductoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import com.example.forest_access.dto.ProductoTratamientoDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoTratamientoService {

    private ProductoTratamientoRepository prodRepo;
    private TratamientoRepository tratamientoRepository;
    private ProductoRepository productoRepository;

    public List<ProductoTratamientoResponse> findAll()
    {
        return prodRepo.findAll().stream().map( PT ->{
            ProductoTratamientoResponse prodTraDTO = new ProductoTratamientoResponse();
            prodTraDTO.setNombreProducto(PT.getProducto().getNombre());
            prodTraDTO.setNombreTratamiento(PT.getTratamiento().getNombre());
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
    public ProductoTratamientoResponse update(Integer idProductoTratamiento, ProductoTratamientoDTO productoTratamientoActualizado) {
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
        ProductoTratamientoResponse ptr = new ProductoTratamientoResponse();
        BeanUtils.copyProperties(existente,ptr);
        ptr.setNombreProducto(existente.getProducto().getNombre());
        ptr.setNombreTratamiento(existente.getTratamiento().getNombre());
        prodRepo.save(existente);
        return ptr;
    }

    @Transactional
    public ProductoTratamientoResponse delete(Integer idProductoTratamiento) {
        if (!prodRepo.existsById(idProductoTratamiento)) {
            throw new RuntimeException("ProductoTratamiento no encontrado con id: " + idProductoTratamiento);
        }
        ProductoTratamiento prod = prodRepo.findById(idProductoTratamiento)
                        .orElseThrow( ()->new RuntimeException("ProductoTratamiento no encotrado"));
        ProductoTratamientoResponse prodTraDTO = new ProductoTratamientoResponse();
        prodTraDTO.setNombreProducto(prod.getProducto().getNombre());
        prodTraDTO.setNombreTratamiento(prod.getTratamiento().getNombre());
        prodTraDTO.setDosis(prod.getDosis());
        prodTraDTO.setUnidad(prod.getUnidad());
        prodRepo.deleteById(idProductoTratamiento);
        return prodTraDTO;
    }
}
