package com.walmart.checkout.domain.port.in;

import com.walmart.checkout.domain.model.CheckoutResult;

import java.util.List;

/**
 * Port In - Primary port for checkout operations
 * This is what the domain offers to the outside world
 */
public interface CheckoutUseCase {

    /**
     * Calculate checkout preview with discounts applied
     */
    CheckoutResult calculatePreview(CheckoutCommand command);

    /**
     * Confirm and process the checkout
     */
    CheckoutResult confirmCheckout(CheckoutCommand command);

    /**
     * Command object for checkout operations
     */
    record CheckoutCommand(
            List<ItemCommand> items,
            String paymentMethodCode
    ) {}

    record ItemCommand(
            Long productId,
            int quantity
    ) {}
}
