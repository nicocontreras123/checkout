package com.walmart.checkout.domain.service;

import com.walmart.checkout.domain.model.Product;
import com.walmart.checkout.domain.port.in.ProductQueryUseCase;
import com.walmart.checkout.domain.port.out.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Domain Service - Implements product query use case
 */
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
