package com.walmart.checkout.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI checkoutOpenAPI() {
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
                .info(info);
    }
}
