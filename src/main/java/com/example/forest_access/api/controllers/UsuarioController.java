package com.example.forest_access.api.controllers;

import com.example.forest_access.api.controllers.response.PunteroUsuarioResponse;
import com.example.forest_access.api.controllers.response.UsuarioResponse;
import com.example.forest_access.biz.dao.services.UsuarioService;
import com.example.forest_access.dto.PunteroUsuarioRequest;
import com.example.forest_access.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.mostrarUsuarios());
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioDTO usuario) {
        UsuarioResponse usu = usuarioService.createUsuario(usuario);
        URI location = URI.create("/forest_access/api/usuarios/" + usu.getId());
        return ResponseEntity.created(location).body(usu);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Integer id,
            @RequestBody UsuarioDTO usuario) {
        return  ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /* ── Puntero-specific endpoints ── */

    @GetMapping("/puntero/all")
    public ResponseEntity<List<PunteroUsuarioResponse>> obtenerPunteroUsuarios() {
        return ResponseEntity.ok(usuarioService.mostrarPunteroUsuarios());
    }

    @PostMapping("/puntero/create")
    public ResponseEntity<PunteroUsuarioResponse> crearPunteroUsuario(@RequestBody PunteroUsuarioRequest request) {
        PunteroUsuarioResponse usu = usuarioService.createPunteroUsuario(request);
        URI location = URI.create("/forest_access/api/usuarios/puntero/" + usu.getId());
        return ResponseEntity.created(location).body(usu);
    }

    @PutMapping("/puntero/update/{id}")
    public ResponseEntity<PunteroUsuarioResponse> actualizarPunteroUsuario(@PathVariable Integer id,
            @RequestBody PunteroUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.updatePunteroUsuario(id, request));
    }

    @DeleteMapping("/puntero/delete/{id}")
    public ResponseEntity<Void> eliminarPunteroUsuario(@PathVariable Integer id) {
        usuarioService.deletePunteroUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/puntero/cambiar-password/{id}")
    public ResponseEntity<PunteroUsuarioResponse> cambiarPasswordPuntero(@PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String currentPassword = body.get("currentPassword");
        String nuevaPassword = body.get("newPassword");
        if (currentPassword == null || currentPassword.isBlank() || nuevaPassword == null || nuevaPassword.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(usuarioService.cambiarPasswordPropio(id, currentPassword, nuevaPassword));
    }
}
