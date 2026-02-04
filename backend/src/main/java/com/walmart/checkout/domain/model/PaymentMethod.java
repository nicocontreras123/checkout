package com.walmart.checkout.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * Domain entity - Payment method with optional discount
 */
@Schema(description = "Método de pago disponible con descuento opcional")
public class PaymentMethod {

    @Schema(description = "ID único del método de pago", example = "1")
    private Long id;
    
    @Schema(description = "Código identificador del método", example = "CREDIT_CARD")
    private String code;
    
    @Schema(description = "Nombre del método de pago", example = "Tarjeta de Crédito")
    private String name;
    
    @Schema(description = "Porcentaje de descuento aplicable", example = "5.0")
    private BigDecimal discountPercentage;
    
    @Schema(description = "Descripción del método de pago", example = "Todas las tarjetas de crédito")
    private String description;
    
    @Schema(description = "Indica si el método está activo", example = "true")
    private boolean active;

    public PaymentMethod() {}

    public PaymentMethod(Long id, String code, String name, BigDecimal discountPercentage, String description, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.description = description;
        this.active = active;
    }

    public boolean hasDiscount() {
        return discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(BigDecimal discountPercentage) { this.discountPercentage = discountPercentage; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
