package com.store.cincomenos.infra.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPI() {
         return new OpenAPI()
            .info(new Info()
                .title("API Cincomenos")
                .description("This API works as a billing and inventory management system, which works with a user authentication and authorization system.")
                .version("v0.9.1")
                .contact(new Contact()
                    .email("cjaguilar1997@gmail.com")
                    .name("Cristoper Aguilar")
                    .url("https://github.com/CJAguilar1997/")))
            .externalDocs(new ExternalDocumentation()
                .description("API Cincomenos Documentation")
                .url("https://github.com/CJAguilar1997/cincomenos"))
            .components(new Components()
                .addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
