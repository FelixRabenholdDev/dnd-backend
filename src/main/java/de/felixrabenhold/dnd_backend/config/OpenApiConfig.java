package de.felixrabenhold.dnd_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("DnD 5e Character Manager API")
                        .version("0.0.1")
                        .description("Backend-API zur Verwaltung von D&D-5e-2024-Charakteren"));
    }
}
