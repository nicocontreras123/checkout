package com.walmart.checkout.infrastructure.adapter.out.persistence.repository;

import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionJpaRepository extends JpaRepository<PromotionEntity, Long> {

    List<PromotionEntity> findByActiveTrue();
}
