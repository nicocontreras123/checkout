package com.walmart.checkout.infrastructure.adapter.out.persistence.repository;

import com.walmart.checkout.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCategory(String category);
}
