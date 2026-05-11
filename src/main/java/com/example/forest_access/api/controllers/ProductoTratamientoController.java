package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.ProductoTratamientoResponse;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.services.ProductoTratamientoService;
import com.example.forest_access.dto.ProductoTratamientoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/producto_tratamientos")
public class ProductoTratamientoController {

    private ProductoTratamientoService prodService;

    public ProductoTratamientoController(ProductoTratamientoService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoTratamientoResponse>> getAll() {

        return ResponseEntity.ok(prodService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductoTratamientoResponse> create(@RequestBody ProductoTratamientoDTO productoTratamiento) {
        ProductoTratamiento nuevo = prodService.create(productoTratamiento);
        ProductoTratamientoResponse ptr = new ProductoTratamientoResponse();
        BeanUtils.copyProperties(nuevo,ptr);
        ptr.setNombreProducto(nuevo.getProducto().getNombre());
        ptr.setNombreTratamiento(nuevo.getTratamiento().getNombre());
        URI location = URI.create("/forest_access/api/producto_tratamientos/" + nuevo.getIdProductoTratamiento());
        return ResponseEntity.created(location).body(ptr);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoTratamientoResponse> update(@PathVariable Integer id,
                                                      @RequestBody ProductoTratamientoDTO productoTratamiento) {
        ProductoTratamientoResponse actualizado = prodService.update(id, productoTratamiento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductoTratamientoResponse> delete(@PathVariable Integer id) {
        ProductoTratamientoResponse prod = prodService.delete(id);
        return ResponseEntity.ok(prod);
    }


}
