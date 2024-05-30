package com.teame1i4.newsfeed.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components()
            .addSecuritySchemes("authorization", SecurityScheme()
                .name("authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
        .addSecurityItem(SecurityRequirement().addList("authorization"))
        .info(
            Info()
                .title("Music Newsfeed API")
                .description("Newsfeed Schema")
                .version("1.0.0")
        )
}