package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.AppliedDiscount;
import com.walmart.checkout.domain.model.Cart;

import java.util.List;

/**
 * Domain Service - Strategy interface for applying discounts
 * Part of the domain layer, no infrastructure dependencies
 */
public interface DiscountStrategy {

    /**
     * Calculates applicable discounts for the given cart
     */
    List<AppliedDiscount> apply(Cart cart);

    /**
     * Order in which this strategy should be applied (lower = first)
     */
    int getOrder();
}
