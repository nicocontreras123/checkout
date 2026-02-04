package com.walmart.checkout.infrastructure.adapter.out.persistence.mapper;

import com.walmart.checkout.domain.model.Product;
import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        return new Product(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getPrice(),
                entity.getCategory(),
                entity.getImageUrl()
        );
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null) return null;
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setSku(domain.getSku());
        entity.setName(domain.getName());
        entity.setPrice(domain.getPrice());
        entity.setCategory(domain.getCategory());
        entity.setImageUrl(domain.getImageUrl());
        return entity;
    }
}
