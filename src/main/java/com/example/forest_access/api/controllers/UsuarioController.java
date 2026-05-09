package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.mostrarUsuarios());
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario usu = usuarioService.createUsuario(usuario);
        URI location = URI.create("/forest_access/api/usuarios/" + usu.getId());
        return ResponseEntity.created(location).body(usu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id,
            @RequestBody Usuario usuario) {
        return  ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.deleteUsuario(id));
    }




}
