package com.example.forest_access.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Activar la configuración de CORS dentro de Spring Security
                .cors(cors -> cors.configurationSource(request -> {
                    var cache = new org.springframework.web.cors.CorsConfiguration();
                    cache.setAllowedOrigins(java.util.List.of("*"));
                    cache.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    cache.setAllowedHeaders(java.util.List.of("*"));
                    return cache;
                }))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new SeguridadConfig(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/forest_access/api/**",    // cubre todos tus endpoints
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public WebMvcConfigurer configuracionCorsGlobal() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/forest_access/api/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOriginPatterns("*")
                        .allowCredentials(true);
            }
        };
    }
}