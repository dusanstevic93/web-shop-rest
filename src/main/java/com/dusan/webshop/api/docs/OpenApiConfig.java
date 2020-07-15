package com.dusan.webshop.api.docs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = OpenApiConfig.BEARER_TOKEN_SCHEME, type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenApiConfig {

    public static final String BEARER_TOKEN_SCHEME = "Bearer token";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("WEB SHOP API"));
    }
}
