package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.*;
import com.walmart.checkout.domain.port.out.PromotionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain Service - Strategy for applying promotion-based discounts
 */
public class PromotionDiscountStrategy implements DiscountStrategy {

    private final PromotionRepository promotionRepository;

    public PromotionDiscountStrategy(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<AppliedDiscount> apply(Cart cart) {
        List<AppliedDiscount> discounts = new ArrayList<>();
        List<Promotion> activePromotions = promotionRepository.findByActiveTrue();

        for (Promotion promotion : activePromotions) {
            if (!promotion.isValidNow()) {
                continue;
            }

            AppliedDiscount discount = calculateDiscount(cart, promotion);
            if (discount != null && discount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                discounts.add(discount);
            }
        }

        return discounts;
    }

    private AppliedDiscount calculateDiscount(Cart cart, Promotion promotion) {
        BigDecimal applicableAmount = getApplicableAmount(cart, promotion);

        if (applicableAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        if (promotion.getMinPurchaseAmount() != null &&
            applicableAmount.compareTo(promotion.getMinPurchaseAmount()) < 0) {
            return null;
        }

        BigDecimal discountAmount;
        String description;

        switch (promotion.getType()) {
            case PERCENTAGE:
                discountAmount = applicableAmount
                        .multiply(promotion.getDiscountValue())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                description = String.format("%s: %s%% off",
                        promotion.getName(),
                        promotion.getDiscountValue().stripTrailingZeros().toPlainString());
                break;
            case FIXED_AMOUNT:
                discountAmount = promotion.getDiscountValue();
                description = String.format("%s: $%s off",
                        promotion.getName(),
                        promotion.getDiscountValue().stripTrailingZeros().toPlainString());
                break;
            default:
                return null;
        }

        return AppliedDiscount.promotion(description, discountAmount, promotion.getCode());
    }

    private BigDecimal getApplicableAmount(Cart cart, Promotion promotion) {
        if (promotion.getApplicableProductId() != null) {
            return cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(promotion.getApplicableProductId()))
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (promotion.getApplicableCategory() != null) {
            return cart.getItems().stream()
                    .filter(item -> promotion.getApplicableCategory().equals(item.getCategory()))
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return cart.calculateSubtotal();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
