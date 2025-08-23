package com.collabera.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info =
@Info(
        title = "Collabera Library API",
        version = "v1",
        description = "API for managing Library tasks"))
public class SwaggerConfig { }
