package com.walmart.checkout.domain.port.in;

import com.walmart.checkout.domain.model.Promotion;

import java.util.List;

/**
 * Port In - Query operations for promotions
 */
public interface PromotionQueryUseCase {

    List<Promotion> findAllActiveAndValid();
}
