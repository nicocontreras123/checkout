package com.walmart.checkout.domain.port.out;

import com.walmart.checkout.domain.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

/**
 * Port Out - Secondary port for payment method persistence
 */
public interface PaymentMethodRepository {

    List<PaymentMethod> findByActiveTrue();

    Optional<PaymentMethod> findByCodeAndActiveTrue(String code);
}
