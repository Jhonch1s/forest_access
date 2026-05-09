package com.example.forest_access.api.controllers;

import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.services.AuthService;
import com.example.forest_access.api.controllers.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // Utilizamos el Optional correctamente con orElseThrow
        Usuario u = authService.login(request.getUsuario(), request.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));
        
        String token = authService.generarToken(u);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
