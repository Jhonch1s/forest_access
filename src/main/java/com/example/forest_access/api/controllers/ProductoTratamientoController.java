package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.services.ProductoTratamientoService;
import com.example.forest_access.dto.ProductoTratamientoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/producto_tratamientos")
public class ProductoTratamientoController {

    private ProductoTratamientoService prodService;

    public ProductoTratamientoController(ProductoTratamientoService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoTratamientoDTO>> getAll() {

        return ResponseEntity.ok(prodService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductoTratamientoDTO> create(@RequestBody ProductoTratamientoDTO productoTratamiento) {
        ProductoTratamiento nuevo = prodService.create(productoTratamiento);
        URI location = URI.create("/forest_access/api/producto_tratamientos/" + nuevo.getIdProductoTratamiento());
        return ResponseEntity.created(location).body(productoTratamiento);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoTratamientoDTO> update(@PathVariable Integer id,
                                                      @RequestBody ProductoTratamientoDTO productoTratamiento) {
        ProductoTratamientoDTO actualizado = prodService.update(id, productoTratamiento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductoTratamientoDTO> delete(@PathVariable Integer id) {
        ProductoTratamientoDTO prod = prodService.delete(id);
        return ResponseEntity.ok(prod);
    }


}
