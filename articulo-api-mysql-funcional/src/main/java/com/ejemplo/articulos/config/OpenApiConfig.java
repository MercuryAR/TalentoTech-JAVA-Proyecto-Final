package com.ejemplo.articulos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST de Productos Deportivos")
                        .version("1.0.0")
                        .description("API para gestión de productos deportivos con herencia y polimorfismo. " +
                                "Soporta tres tipos de productos: Remeras (10% descuento), " +
                                "Zapatillas (15% descuento) y Pelotas (sin descuento).")
                        .contact(new Contact()
                                .name("Paulo Orsini")
                                .email("paulo.orsini@example.com")
                                .url("https://github.com/pauloorsini"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo")
                ));
    }
}
