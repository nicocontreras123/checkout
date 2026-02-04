package com.walmart.checkout.domain.port.out;

import com.walmart.checkout.domain.model.Promotion;

import java.util.List;

/**
 * Port Out - Secondary port for promotion persistence
 */
public interface PromotionRepository {

    List<Promotion> findByActiveTrue();
}
