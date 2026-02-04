package com.walmart.checkout.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Solicitud de checkout con items del carrito y método de pago")
public class CheckoutRequest {

    @Schema(
            description = "Lista de items en el carrito",
            example = "[{\"productId\": 1, \"quantity\": 2}, {\"productId\": 3, \"quantity\": 1}]",
            required = true
    )
    @NotEmpty(message = "Cart must have at least one item")
    @Valid
    private List<CartItemRequest> items;

    @Schema(
            description = "Código del método de pago seleccionado",
            example = "CREDIT_CARD",
            required = true,
            allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "CASH", "WALMART_CARD"}
    )
    @NotNull(message = "Payment method is required")
    private String paymentMethodCode;

    public CheckoutRequest() {}

    public CheckoutRequest(List<CartItemRequest> items, String paymentMethodCode) {
        this.items = items;
        this.paymentMethodCode = paymentMethodCode;
    }

    public List<CartItemRequest> getItems() { return items; }
    public void setItems(List<CartItemRequest> items) { this.items = items; }
    public String getPaymentMethodCode() { return paymentMethodCode; }
    public void setPaymentMethodCode(String paymentMethodCode) { this.paymentMethodCode = paymentMethodCode; }
}
