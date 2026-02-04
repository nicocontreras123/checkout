package com.walmart.checkout.domain.port.in;

import com.walmart.checkout.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Port In - Query operations for products
 */
public interface ProductQueryUseCase {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(String category);
}
