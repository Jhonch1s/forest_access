package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    @Value("${jwt.secret}")
    private String clave;

    public Optional<Usuario> login(String usuario, String password) {
        // Obtenemos el usuario por su nombre.
        // El .filter verificará que la contraseña coincida. 
        // Si no coincide o el usuario no existe, devuelve Optional.empty()
        return usuarioRepository.findByNombreUsuario(usuario)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }

    public String generarToken(Usuario usuario) {
        // En caso de que no tenga perfiles, evitamos un NullPointerException
        if (usuario.getPerfiles() == null) {
            usuario.setPerfiles(List.of());
        }

        String[] perfilesbd = usuario.getPerfiles().stream()
                .map(p -> p.getNombre())
                .toArray(String[]::new);
                
        List<GrantedAuthority> perfiles = AuthorityUtils.createAuthorityList(perfilesbd);

        return Jwts.builder()
                .setId("TIP2026")
                .setSubject(usuario.getNombreUsuario())
                .claim("authorities", perfiles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(SignatureAlgorithm.HS256, clave.getBytes())
                .compact();
    }
}
