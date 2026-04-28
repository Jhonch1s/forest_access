package com.example.forest_access.biz.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String nombreUsuario;
    private String password;

    @ManyToMany
    private List<Perfil> perfiles;


}