package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.services.ProductoTratamientoService;
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
    public ResponseEntity<List<ProductoTratamiento>> getAll() {
        return ResponseEntity.ok(prodService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductoTratamiento> create(@RequestBody ProductoTratamiento productoTratamiento) {
        ProductoTratamiento nuevo = prodService.create(productoTratamiento);
        URI location = URI.create("/forest_access/api/producto_tratamientos/" + nuevo.getIdProductoTratamiento());
        return ResponseEntity.created(location).body(nuevo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoTratamiento> update(@PathVariable Integer id,
                                                      @RequestBody ProductoTratamiento productoTratamiento) {
        ProductoTratamiento actualizado = prodService.update(id, productoTratamiento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        prodService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
