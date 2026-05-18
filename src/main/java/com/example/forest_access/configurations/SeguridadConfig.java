package com.example.forest_access.configurations;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SeguridadConfig extends OncePerRequestFilter {

    private final String clave;

    public SeguridadConfig(String clave) {
        this.clave = clave;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Si hay algo que parece un token en la cabecera, intentamos validarlo.
            if (validarUsoDeToken(request)) {
                Claims claims = validarToken(request);
                if (claims.get("authorities") != null) {
                    crearAutorizacion(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception e) {
            // Si el token es inválido, expirado o malformado, limpiamos el contexto.
            // No bloqueamos la petición aquí; dejamos que la configuración de HttpSecurity
            // decida si el recurso solicitado es de libre acceso o requiere autenticación.
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private boolean validarUsoDeToken(HttpServletRequest request) {
        String autenticacion = request.getHeader("Authorization");
        return autenticacion != null && autenticacion.startsWith("Bearer");
    }

    private Claims validarToken(HttpServletRequest request) {
        String autenticacion = request.getHeader("Authorization").replace("Bearer ", "");
        return Jwts.parser()
                .setSigningKey(clave.getBytes())
                .parseClaimsJws(autenticacion)
                .getBody();
    }

    private void crearAutorizacion(Claims claims) {
        List<String> perfiles = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                perfiles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}