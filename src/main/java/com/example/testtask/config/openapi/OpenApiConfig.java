package com.example.testtask.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {
    public static final String SWAGGER_TITLE = "springdoc.swagger.title";
    public static final String SWAGGER_DESCRIPTION = "springdoc.swagger.description";
    public static final String SWAGGER_VERSION = "springdoc.swagger.version";

    private final Environment environment;

    public OpenApiConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title(environment.getProperty(SWAGGER_TITLE))
                        .description(environment.getProperty(SWAGGER_DESCRIPTION))
                        .version(environment.getProperty(SWAGGER_VERSION)));

    }
}
