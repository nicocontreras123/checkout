package com.walmart.checkout.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI checkoutOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor de desarrollo local");

        Server productionServer = new Server();
        productionServer.setUrl("https://api.walmart.cl");
        productionServer.setDescription("Servidor de producción");

        Contact contact = new Contact();
        contact.setName("Walmart Chile Tech Team");
        contact.setEmail("tech@walmart.cl");
        contact.setUrl("https://www.walmart.cl");

        License license = new License();
        license.setName("Propietario - Walmart Chile");

        Info info = new Info()
                .title("Walmart Chile - API de Checkout")
                .version("1.0.0")
                .contact(contact)
                .description("API REST para el sistema de checkout de Walmart Chile. " +
                        "Permite consultar productos, métodos de pago, promociones y procesar órdenes de compra " +
                        "con cálculo automático de descuentos.")
                .termsOfService("https://www.walmart.cl/terminos-y-condiciones")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
