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
        info.setTitle("Agencia Turismo");
        info.setDescription("Agencia Turismo");
        return new OpenAPI().info(
                info
        );
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("Agencia Turismo").pathsToMatch("/api/v1/**").build();
    }
}
