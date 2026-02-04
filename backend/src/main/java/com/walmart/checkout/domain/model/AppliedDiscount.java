package com.walmart.checkout.domain.model;

import java.math.BigDecimal;

/**
 * Domain value object - Represents an applied discount
 */
public class AppliedDiscount {

    private String type;
    private String description;
    private BigDecimal amount;
    private String promotionCode;

    public AppliedDiscount() {}

    public AppliedDiscount(String type, String description, BigDecimal amount, String promotionCode) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.promotionCode = promotionCode;
    }

    public static AppliedDiscount promotion(String description, BigDecimal amount, String code) {
        return new AppliedDiscount("PROMOTION", description, amount, code);
    }

    public static AppliedDiscount paymentMethod(String description, BigDecimal amount) {
        return new AppliedDiscount("PAYMENT_METHOD", description, amount, null);
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getPromotionCode() { return promotionCode; }
    public void setPromotionCode(String promotionCode) { this.promotionCode = promotionCode; }
}
