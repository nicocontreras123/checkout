package com.walmart.checkout.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

/**
 * Domain value object - Result of checkout calculation
 */
@Schema(description = "Resultado del cálculo de checkout con totales y descuentos aplicados")
public class CheckoutResult {

    @Schema(description = "Items en el carrito con información de producto y cantidad")
    private List<CartItem> items;
    
    @Schema(description = "Cantidad total de items", example = "5")
    private int totalItems;
    
    @Schema(description = "Subtotal antes de descuentos", example = "125000")
    private BigDecimal subtotal;
    
    @Schema(description = "Lista de descuentos aplicados")
    private List<AppliedDiscount> discounts;
    
    @Schema(description = "Total de descuentos aplicados", example = "15000")
    private BigDecimal totalDiscount;
    
    @Schema(description = "Total final a pagar", example = "110000")
    private BigDecimal total;
    
    @Schema(description = "Nombre del método de pago seleccionado", example = "Tarjeta de Crédito")
    private String paymentMethod;
    
    @Schema(description = "Estado de la orden", example = "CONFIRMED")
    private String status;
    
    @Schema(description = "Código de confirmación único", example = "WMT-20260204-ABC123")
    private String confirmationCode;

    public CheckoutResult() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CheckoutResult result = new CheckoutResult();

        public Builder items(List<CartItem> items) {
            result.items = items;
            return this;
        }

        public Builder totalItems(int totalItems) {
            result.totalItems = totalItems;
            return this;
        }

        public Builder subtotal(BigDecimal subtotal) {
            result.subtotal = subtotal;
            return this;
        }

        public Builder discounts(List<AppliedDiscount> discounts) {
            result.discounts = discounts;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            result.paymentMethod = paymentMethod;
            return this;
        }

        public Builder status(String status) {
            result.status = status;
            return this;
        }

        public Builder confirmationCode(String confirmationCode) {
            result.confirmationCode = confirmationCode;
            return this;
        }

        public Builder calculateTotals() {
            if (result.discounts != null) {
                result.totalDiscount = result.discounts.stream()
                        .map(AppliedDiscount::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            } else {
                result.totalDiscount = BigDecimal.ZERO;
            }

            if (result.subtotal != null) {
                result.total = result.subtotal.subtract(result.totalDiscount);
                if (result.total.compareTo(BigDecimal.ZERO) < 0) {
                    result.total = BigDecimal.ZERO;
                }
            }
            return this;
        }

        public CheckoutResult build() {
            return result;
        }
    }

    // Getters and Setters
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public List<AppliedDiscount> getDiscounts() { return discounts; }
    public void setDiscounts(List<AppliedDiscount> discounts) { this.discounts = discounts; }
    public BigDecimal getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(BigDecimal totalDiscount) { this.totalDiscount = totalDiscount; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getConfirmationCode() { return confirmationCode; }
    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }
}
