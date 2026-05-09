package com.example.forest_access.api.controllers.response;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombreUsuario;
    private List<PerfilResponse> perfiles;
}
