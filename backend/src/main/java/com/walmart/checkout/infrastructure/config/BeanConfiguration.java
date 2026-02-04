package com.walmart.checkout.infrastructure.config;

import com.walmart.checkout.domain.port.in.CheckoutUseCase;
import com.walmart.checkout.domain.port.in.PaymentMethodQueryUseCase;
import com.walmart.checkout.domain.port.in.ProductQueryUseCase;
import com.walmart.checkout.domain.port.in.PromotionQueryUseCase;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;
import com.walmart.checkout.domain.port.out.ProductRepository;
import com.walmart.checkout.domain.port.out.PromotionRepository;
import com.walmart.checkout.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Configuration - Wires domain services with infrastructure adapters
 * This is where the hexagonal architecture comes together
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public DiscountStrategy promotionDiscountStrategy(PromotionRepository promotionRepository) {
        return new PromotionDiscountStrategy(promotionRepository);
    }

    @Bean
    public DiscountStrategy paymentMethodDiscountStrategy(PaymentMethodRepository paymentMethodRepository) {
        return new PaymentMethodDiscountStrategy(paymentMethodRepository);
    }

    @Bean
    public CheckoutUseCase checkoutUseCase(
            ProductRepository productRepository,
            PaymentMethodRepository paymentMethodRepository,
            List<DiscountStrategy> discountStrategies) {
        return new CheckoutService(productRepository, paymentMethodRepository, discountStrategies);
    }

    @Bean
    public ProductQueryUseCase productQueryUseCase(ProductRepository productRepository) {
        return new ProductQueryService(productRepository);
    }

    @Bean
    public PaymentMethodQueryUseCase paymentMethodQueryUseCase(PaymentMethodRepository paymentMethodRepository) {
        return new PaymentMethodQueryService(paymentMethodRepository);
    }

    @Bean
    public PromotionQueryUseCase promotionQueryUseCase(PromotionRepository promotionRepository) {
        return new PromotionQueryService(promotionRepository);
    }
}
