package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/create")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.create(producto);
        URI location = URI.create("/forest_access/api/productos/" + nuevoProducto.getIdProducto());
        return ResponseEntity.created(location).body(nuevoProducto);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Integer id,
            @RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
