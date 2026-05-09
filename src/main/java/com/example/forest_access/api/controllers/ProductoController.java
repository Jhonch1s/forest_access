package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.services.ProductoService;
import com.example.forest_access.dto.ProductoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forest_access/api/productos")
public class ProductoController {
    private ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO producto) {
        Producto nuevoProducto = productoService.create(producto);
        URI location = URI.create("/forest_access/api/productos/" + nuevoProducto.getIdProducto());
        return ResponseEntity.created(location).body(producto);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(
            @PathVariable Integer id,
            @RequestBody ProductoDTO producto) {
        ProductoDTO productoActualizado = productoService.update(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductoDTO> deleteProducto(@PathVariable Integer id) {
        ProductoDTO p= productoService.delete(id);
        return ResponseEntity.ok(p);
    }

}
