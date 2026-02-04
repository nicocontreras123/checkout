package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.*;
import com.walmart.checkout.domain.port.in.CheckoutUseCase;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;
import com.walmart.checkout.domain.port.out.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Domain Service - Implements the checkout use case
 * Orchestrates the checkout process using domain entities and strategies
 */
public class CheckoutService implements CheckoutUseCase {

    private final ProductRepository productRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final List<DiscountStrategy> discountStrategies;

    public CheckoutService(
            ProductRepository productRepository,
            PaymentMethodRepository paymentMethodRepository,
            List<DiscountStrategy> discountStrategies) {
        this.productRepository = productRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.discountStrategies = discountStrategies;
    }

    @Override
    public CheckoutResult calculatePreview(CheckoutCommand command) {
        Cart cart = buildCart(command);
        BigDecimal subtotal = cart.calculateSubtotal();
        List<AppliedDiscount> discounts = applyDiscounts(cart);

        String paymentMethodName = paymentMethodRepository
                .findByCodeAndActiveTrue(command.paymentMethodCode())
                .map(PaymentMethod::getName)
                .orElse(command.paymentMethodCode());

        return CheckoutResult.builder()
                .items(cart.getItems())
                .totalItems(cart.getTotalItems())
                .subtotal(subtotal)
                .discounts(discounts)
                .paymentMethod(paymentMethodName)
                .status("PREVIEW")
                .calculateTotals()
                .build();
    }

    @Override
    public CheckoutResult confirmCheckout(CheckoutCommand command) {
        Cart cart = buildCart(command);
        BigDecimal subtotal = cart.calculateSubtotal();
        List<AppliedDiscount> discounts = applyDiscounts(cart);

        PaymentMethod paymentMethod = paymentMethodRepository
                .findByCodeAndActiveTrue(command.paymentMethodCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid payment method: " + command.paymentMethodCode()));

        String confirmationCode = generateConfirmationCode();

        return CheckoutResult.builder()
                .items(cart.getItems())
                .totalItems(cart.getTotalItems())
                .subtotal(subtotal)
                .discounts(discounts)
                .paymentMethod(paymentMethod.getName())
                .status("CONFIRMED")
                .confirmationCode(confirmationCode)
                .calculateTotals()
                .build();
    }

    private Cart buildCart(CheckoutCommand command) {
        List<CartItem> cartItems = new ArrayList<>();

        for (ItemCommand itemCommand : command.items()) {
            Product product = productRepository.findById(itemCommand.productId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product not found: " + itemCommand.productId()));

            CartItem cartItem = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getSku(),
                    product.getCategory(),
                    itemCommand.quantity(),
                    product.getPrice()
            );
            cartItems.add(cartItem);
        }

        return new Cart(cartItems, command.paymentMethodCode());
    }

    private List<AppliedDiscount> applyDiscounts(Cart cart) {
        List<AppliedDiscount> allDiscounts = new ArrayList<>();

        discountStrategies.stream()
                .sorted(Comparator.comparingInt(DiscountStrategy::getOrder))
                .forEach(strategy -> allDiscounts.addAll(strategy.apply(cart)));

        return allDiscounts;
    }

    private String generateConfirmationCode() {
        return "WMT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
