package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.AppliedDiscount;
import com.walmart.checkout.domain.model.Cart;
import com.walmart.checkout.domain.model.PaymentMethod;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

/**
 * Domain Service - Strategy for applying payment method discounts
 */
public class PaymentMethodDiscountStrategy implements DiscountStrategy {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodDiscountStrategy(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<AppliedDiscount> apply(Cart cart) {
        if (cart.getPaymentMethodCode() == null || cart.getPaymentMethodCode().isBlank()) {
            return Collections.emptyList();
        }

        PaymentMethod paymentMethod = paymentMethodRepository
                .findByCodeAndActiveTrue(cart.getPaymentMethodCode())
                .orElse(null);

        if (paymentMethod == null || !paymentMethod.hasDiscount()) {
            return Collections.emptyList();
        }

        BigDecimal subtotal = cart.calculateSubtotal();
        BigDecimal discountAmount = subtotal
                .multiply(paymentMethod.getDiscountPercentage())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        String description = String.format("%s: %s%% off",
                paymentMethod.getName(),
                paymentMethod.getDiscountPercentage().stripTrailingZeros().toPlainString());

        return Collections.singletonList(AppliedDiscount.paymentMethod(description, discountAmount));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
