package com.walmart.checkout.infrastructure.adapter.out.persistence.mapper;

import com.walmart.checkout.domain.model.PaymentMethod;
import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.PaymentMethodEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    public PaymentMethod toDomain(PaymentMethodEntity entity) {
        if (entity == null) return null;
        return new PaymentMethod(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDiscountPercentage(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    public PaymentMethodEntity toEntity(PaymentMethod domain) {
        if (domain == null) return null;
        PaymentMethodEntity entity = new PaymentMethodEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setDiscountPercentage(domain.getDiscountPercentage());
        entity.setDescription(domain.getDescription());
        entity.setActive(domain.isActive());
        return entity;
    }
}
