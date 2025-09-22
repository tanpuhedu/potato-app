package com.ktpm.potatoapi.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Potato API document")
                .version("v1.0.0")
                .description("Potato API Service");

        return new OpenAPI().info(info);
    }
}
