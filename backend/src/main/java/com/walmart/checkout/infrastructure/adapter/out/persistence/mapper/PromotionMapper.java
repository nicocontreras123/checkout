package com.walmart.checkout.infrastructure.adapter.out.persistence.mapper;

import com.walmart.checkout.domain.model.Promotion;
import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.PromotionEntity;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    public Promotion toDomain(PromotionEntity entity) {
        if (entity == null) return null;
        Promotion promotion = new Promotion();
        promotion.setId(entity.getId());
        promotion.setCode(entity.getCode());
        promotion.setName(entity.getName());
        promotion.setType(Promotion.PromotionType.valueOf(entity.getType().name()));
        promotion.setDiscountValue(entity.getDiscountValue());
        promotion.setMinPurchaseAmount(entity.getMinPurchaseAmount());
        promotion.setApplicableCategory(entity.getApplicableCategory());
        promotion.setApplicableProductId(entity.getApplicableProductId());
        promotion.setStartDate(entity.getStartDate());
        promotion.setEndDate(entity.getEndDate());
        promotion.setActive(entity.isActive());
        return promotion;
    }

    public PromotionEntity toEntity(Promotion domain) {
        if (domain == null) return null;
        PromotionEntity entity = new PromotionEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setType(PromotionEntity.PromotionType.valueOf(domain.getType().name()));
        entity.setDiscountValue(domain.getDiscountValue());
        entity.setMinPurchaseAmount(domain.getMinPurchaseAmount());
        entity.setApplicableCategory(domain.getApplicableCategory());
        entity.setApplicableProductId(domain.getApplicableProductId());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setActive(domain.isActive());
        return entity;
    }
}
