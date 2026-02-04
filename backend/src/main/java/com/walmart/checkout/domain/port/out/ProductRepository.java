package com.walmart.checkout.domain.port.out;

import com.walmart.checkout.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Port Out - Secondary port for product persistence
 * The domain defines what it needs, infrastructure implements it
 */
public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(String category);
}
