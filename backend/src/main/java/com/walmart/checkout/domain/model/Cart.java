package com.walmart.checkout.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain aggregate root - Shopping cart
 */
public class Cart {

    private List<CartItem> items = new ArrayList<>();
    private String paymentMethodCode;

    public Cart() {}

    public Cart(List<CartItem> items, String paymentMethodCode) {
        this.items = items != null ? items : new ArrayList<>();
        this.paymentMethodCode = paymentMethodCode;
    }

    public BigDecimal calculateSubtotal() {
        return items.stream()
                .map(CartItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public String getPaymentMethodCode() { return paymentMethodCode; }
    public void setPaymentMethodCode(String paymentMethodCode) { this.paymentMethodCode = paymentMethodCode; }
}
