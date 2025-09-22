package com.ktpm.potatoapi.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OpenApiConfig {

    String jwtScheme = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Potato API document")
                .version("v1.0.0")
                .description("Potato API Service");

        return new OpenAPI().info(info).addSecurityItem(new SecurityRequirement()
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
