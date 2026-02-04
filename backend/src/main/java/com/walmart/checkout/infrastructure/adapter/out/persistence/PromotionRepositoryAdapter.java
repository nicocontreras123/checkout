package com.walmart.checkout.infrastructure.adapter.out.persistence;

import com.walmart.checkout.domain.model.Promotion;
import com.walmart.checkout.domain.port.out.PromotionRepository;
import com.walmart.checkout.infrastructure.adapter.out.persistence.mapper.PromotionMapper;
import com.walmart.checkout.infrastructure.adapter.out.persistence.repository.PromotionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter Out - Implements the domain's PromotionRepository port using JPA
 */
@Repository
public class PromotionRepositoryAdapter implements PromotionRepository {

    private final PromotionJpaRepository jpaRepository;
    private final PromotionMapper mapper;

    public PromotionRepositoryAdapter(PromotionJpaRepository jpaRepository, PromotionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Promotion> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
