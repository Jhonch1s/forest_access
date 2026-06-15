package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwt.secret}")
    private String clave;

    @Transactional
    public Optional<Usuario> login(String usuario, String password) {
        Optional<Usuario> optUsuario = usuarioRepository.findByNombreUsuario(usuario);

        if (optUsuario.isEmpty()) {
            return Optional.empty();
        }

        Usuario u = optUsuario.get();
        String storedPassword = u.getPassword();

        if (storedPassword == null) {
            return Optional.empty();
        }

        boolean matches = passwordEncoder.matches(password, storedPassword);

        return matches ? Optional.of(u) : Optional.empty();
    }

    private boolean isBCryptHash(String password) {
        return password != null && password.startsWith("$2");
    }

    public String generarToken(Usuario usuario) {
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
                .claim("idEmpleado", usuario.getEmpleado() != null ? usuario.getEmpleado().getIdEmpleado() : null)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, clave.getBytes())
                .compact();
    }
}
