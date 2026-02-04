package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.PaymentMethod;
import com.walmart.checkout.domain.port.in.PaymentMethodQueryUseCase;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;

import java.util.List;

/**
 * Domain Service - Implements payment method query use case
 */
public class PaymentMethodQueryService implements PaymentMethodQueryUseCase {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodQueryService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<PaymentMethod> findAllActive() {
        return paymentMethodRepository.findByActiveTrue();
    }
}
