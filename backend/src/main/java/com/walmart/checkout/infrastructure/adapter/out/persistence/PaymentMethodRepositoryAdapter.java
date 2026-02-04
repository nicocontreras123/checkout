package com.walmart.checkout.infrastructure.adapter.out.persistence;

import com.walmart.checkout.domain.model.PaymentMethod;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;
import com.walmart.checkout.infrastructure.adapter.out.persistence.mapper.PaymentMethodMapper;
import com.walmart.checkout.infrastructure.adapter.out.persistence.repository.PaymentMethodJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter Out - Implements the domain's PaymentMethodRepository port using JPA
 */
@Repository
public class PaymentMethodRepositoryAdapter implements PaymentMethodRepository {

    private final PaymentMethodJpaRepository jpaRepository;
    private final PaymentMethodMapper mapper;

    public PaymentMethodRepositoryAdapter(PaymentMethodJpaRepository jpaRepository, PaymentMethodMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PaymentMethod> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentMethod> findByCodeAndActiveTrue(String code) {
        return jpaRepository.findByCodeAndActiveTrue(code).map(mapper::toDomain);
    }
}
