package com.walmart.checkout.infrastructure.adapter.out.persistence.repository;

import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethodEntity, Long> {

    List<PaymentMethodEntity> findByActiveTrue();

    Optional<PaymentMethodEntity> findByCodeAndActiveTrue(String code);
}
