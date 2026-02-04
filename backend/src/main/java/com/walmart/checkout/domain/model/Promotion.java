package com.walmart.checkout.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Domain entity - Promotion with validity rules
 */
@Schema(description = "Promoción aplicable a productos o categorías específicas")
public class Promotion {

    @Schema(description = "ID único de la promoción", example = "1")
    private Long id;
    
    @Schema(description = "Código de la promoción", example = "CYBER2026")
    private String code;
    
    @Schema(description = "Nombre de la promoción", example = "Cyber Monday 2026")
    private String name;
    
    @Schema(description = "Tipo de descuento", example = "PERCENTAGE")
    private PromotionType type;
    
    @Schema(description = "Valor del descuento", example = "15.0")
    private BigDecimal discountValue;
    
    @Schema(description = "Monto mínimo de compra", example = "50000")
    private BigDecimal minPurchaseAmount;
    
    @Schema(description = "Categoría aplicable", example = "Electrónica")
    private String applicableCategory;
    
    @Schema(description = "ID del producto aplicable", example = "5")
    private Long applicableProductId;
    
    @Schema(description = "Fecha de inicio", example = "2026-02-01")
    private LocalDate startDate;
    
    @Schema(description = "Fecha de fin", example = "2026-02-28")
    private LocalDate endDate;
    
    @Schema(description = "Indica si la promoción está activa", example = "true")
    private boolean active;

    @Schema(description = "Tipo de promoción disponible")
    public enum PromotionType {
        @Schema(description = "Descuento porcentual")
        PERCENTAGE,
        @Schema(description = "Descuento de monto fijo")
        FIXED_AMOUNT,
        @Schema(description = "Compra X lleva Y")
        BUY_X_GET_Y
    }

    public Promotion() {}

    public boolean isValidNow() {
        LocalDate today = LocalDate.now();
        boolean afterStart = startDate == null || !today.isBefore(startDate);
        boolean beforeEnd = endDate == null || !today.isAfter(endDate);
        return active && afterStart && beforeEnd;
    }

    public boolean appliesToCategory(String category) {
        return applicableCategory != null && applicableCategory.equals(category);
    }

    public boolean appliesToProduct(Long productId) {
        return applicableProductId != null && applicableProductId.equals(productId);
    }

    public boolean isGlobal() {
        return applicableCategory == null && applicableProductId == null;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public PromotionType getType() { return type; }
    public void setType(PromotionType type) { this.type = type; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }
    public BigDecimal getMinPurchaseAmount() { return minPurchaseAmount; }
    public void setMinPurchaseAmount(BigDecimal minPurchaseAmount) { this.minPurchaseAmount = minPurchaseAmount; }
    public String getApplicableCategory() { return applicableCategory; }
    public void setApplicableCategory(String applicableCategory) { this.applicableCategory = applicableCategory; }
    public Long getApplicableProductId() { return applicableProductId; }
    public void setApplicableProductId(Long applicableProductId) { this.applicableProductId = applicableProductId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
