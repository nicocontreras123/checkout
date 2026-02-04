package com.walmart.checkout.domain.model;

import java.math.BigDecimal;

/**
 * Domain value object - Item in shopping cart
 */
public class CartItem {

    private Long productId;
    private String productName;
    private String productSku;
    private String category;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public CartItem() {}

    public CartItem(Long productId, String productName, String productSku, String category, int quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productSku = productSku;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = calculateSubtotal();
    }

    public BigDecimal calculateSubtotal() {
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return this.subtotal;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
