package com.walmart.checkout.domain.port.in;

import com.walmart.checkout.domain.model.PaymentMethod;

import java.util.List;

/**
 * Port In - Query operations for payment methods
 */
public interface PaymentMethodQueryUseCase {

    List<PaymentMethod> findAllActive();
}
