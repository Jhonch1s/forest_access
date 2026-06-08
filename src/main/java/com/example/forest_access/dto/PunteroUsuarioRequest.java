package com.example.forest_access.dto;

import lombok.Data;

@Data
public class PunteroUsuarioRequest {
    private String nombreUsuario;
    private String password;
    private Integer idEmpleado;
}
