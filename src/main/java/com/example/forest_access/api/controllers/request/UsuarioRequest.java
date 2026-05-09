package com.example.forest_access.api.controllers.request;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioRequest {
    private String nombreUsuario;
    private String password;
    private List<Long> idsPerfil;

}
