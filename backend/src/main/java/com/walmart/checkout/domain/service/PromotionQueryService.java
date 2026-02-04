package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.Promotion;
import com.walmart.checkout.domain.port.in.PromotionQueryUseCase;
import com.walmart.checkout.domain.port.out.PromotionRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Domain Service - Implements promotion query use case
 */
public class PromotionQueryService implements PromotionQueryUseCase {

    private final PromotionRepository promotionRepository;

    public PromotionQueryService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Promotion> findAllActiveAndValid() {
        return promotionRepository.findByActiveTrue().stream()
                .filter(Promotion::isValidNow)
                .collect(Collectors.toList());
    }
}
