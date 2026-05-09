package com.example.forest_access.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI config() {
        Info info = new Info();
        info.setTitle("Forest Access");
        info.setDescription("Sistema de gestión forestal");
        return new OpenAPI().info(info);
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Forest Access")
                .pathsToMatch("/api/**")
                .build();
    }
}
