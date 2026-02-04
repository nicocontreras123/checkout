package com.walmart.checkout.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Item del carrito con producto y cantidad")
public class CartItemRequest {

    @Schema(description = "ID del producto", example = "1", required = true)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(description = "Cantidad de unidades del producto", example = "2", required = true, minimum = "1")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public CartItemRequest() {}

    public CartItemRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
