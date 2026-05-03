package com.example.forest_access.configurations;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SeguridadConfig extends OncePerRequestFilter {

    private final String clave = "TIP2026";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // SI NO HAY TOKEN, dejamos pasar la petición para que
            // decida la configuración de HttpSecurity (donde pusiste permitAll)
            if (!validarUsoDeToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Si hay algo que parece un token, lo validamos
            Claims claims = validarToken(request);
            if (claims.get("authorities") != null) {
                crearAutorizacion(claims);
            } else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido o expirado");
        }
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
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        perfiles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}