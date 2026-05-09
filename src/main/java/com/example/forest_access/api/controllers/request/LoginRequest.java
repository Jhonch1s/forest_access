package com.example.forest_access.api.controllers.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String usuario;
    private String password;
}
