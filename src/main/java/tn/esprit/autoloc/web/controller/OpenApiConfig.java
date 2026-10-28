package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI configuration.
 *
 * Swagger UI is available at: http://localhost:8082/api/swagger-ui.html
 * JSON spec is available at:  http://localhost:8082/api/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI autolocOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc API")
                        .description("API REST de gestion de location de véhicules — Atelier 5 ASI 26-27")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("UP ASI — ESPRIT")
                                .email("asi@esprit.tn"))
                        .license(new License()
                                .name("ESPRIT Academic Use")));
    }
}
