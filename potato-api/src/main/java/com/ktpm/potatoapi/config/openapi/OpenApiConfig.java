package com.ktpm.potatoapi.config.openapi;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.server-url}")
    private String serverUrl;

    private String jwtScheme = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Potato API document")
                .version("v1.0.0")
                .description("Potato API Service");

        return new OpenAPI().info(info)
                .servers(List.of(
                        new Server().url(serverUrl + "/potato-api")
                ))
                .addSecurityItem(new SecurityRequirement()
                        .addList(jwtScheme)
                )
                .components(new io.swagger.v3.oas.models.Components()
                        // Jwt (Bearer) Auth
                        .addSecuritySchemes(jwtScheme,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name(jwtScheme)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }
}
