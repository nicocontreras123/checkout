package com.walmart.checkout;

import com.walmart.checkout.domain.model.*;
import com.walmart.checkout.domain.port.in.CheckoutUseCase;
import com.walmart.checkout.domain.port.in.CheckoutUseCase.CheckoutCommand;
import com.walmart.checkout.domain.port.in.CheckoutUseCase.ItemCommand;
import com.walmart.checkout.domain.port.out.PaymentMethodRepository;
import com.walmart.checkout.domain.port.out.ProductRepository;
import com.walmart.checkout.domain.port.out.PromotionRepository;
import com.walmart.checkout.domain.service.CheckoutService;
import com.walmart.checkout.domain.service.DiscountStrategy;
import com.walmart.checkout.domain.service.PaymentMethodDiscountStrategy;
import com.walmart.checkout.domain.service.PromotionDiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private PromotionRepository promotionRepository;

    private CheckoutUseCase checkoutService;

    @BeforeEach
    void setUp() {
        DiscountStrategy promotionStrategy = new PromotionDiscountStrategy(promotionRepository);
        DiscountStrategy paymentStrategy = new PaymentMethodDiscountStrategy(paymentMethodRepository);

        checkoutService = new CheckoutService(
                productRepository,
                paymentMethodRepository,
                Arrays.asList(promotionStrategy, paymentStrategy)
        );
    }

    @Test
    void shouldCalculateSubtotalCorrectly() {
        Product milk = new Product(1L, "WMT-001", "Milk", new BigDecimal("3.48"), "Dairy", null);
        PaymentMethod cash = new PaymentMethod(1L, "CASH", "Cash", BigDecimal.ZERO, null, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(milk));
        when(paymentMethodRepository.findByCodeAndActiveTrue("CASH")).thenReturn(Optional.of(cash));
        when(promotionRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        CheckoutCommand command = new CheckoutCommand(
                List.of(new ItemCommand(1L, 2)),
                "CASH"
        );

        CheckoutResult result = checkoutService.calculatePreview(command);

        assertEquals(new BigDecimal("6.96"), result.getSubtotal());
        assertEquals(new BigDecimal("6.96"), result.getTotal());
        assertEquals(2, result.getTotalItems());
    }

    @Test
    void shouldApplyPaymentMethodDiscount() {
        Product product = new Product(1L, "WMT-001", "Product", new BigDecimal("100.00"), "General", null);
        PaymentMethod debit = new PaymentMethod(1L, "DEBIT", "Debit Card", new BigDecimal("10"), null, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(paymentMethodRepository.findByCodeAndActiveTrue("DEBIT")).thenReturn(Optional.of(debit));
        when(promotionRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        CheckoutCommand command = new CheckoutCommand(
                List.of(new ItemCommand(1L, 1)),
                "DEBIT"
        );

        CheckoutResult result = checkoutService.calculatePreview(command);

        assertEquals(new BigDecimal("100.00"), result.getSubtotal());
        assertEquals(new BigDecimal("10.00"), result.getTotalDiscount());
        assertEquals(new BigDecimal("90.00"), result.getTotal());
        assertEquals(1, result.getDiscounts().size());
        assertEquals("PAYMENT_METHOD", result.getDiscounts().get(0).getType());
    }

    @Test
    void shouldApplyPromotionDiscount() {
        Product dairy = new Product(1L, "WMT-001", "Milk", new BigDecimal("10.00"), "Dairy", null);
        PaymentMethod cash = new PaymentMethod(1L, "CASH", "Cash", BigDecimal.ZERO, null, true);

        Promotion dairyPromo = new Promotion();
        dairyPromo.setCode("DAIRY10");
        dairyPromo.setName("10% Off Dairy");
        dairyPromo.setType(Promotion.PromotionType.PERCENTAGE);
        dairyPromo.setDiscountValue(new BigDecimal("10"));
        dairyPromo.setApplicableCategory("Dairy");
        dairyPromo.setActive(true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(dairy));
        when(paymentMethodRepository.findByCodeAndActiveTrue("CASH")).thenReturn(Optional.of(cash));
        when(promotionRepository.findByActiveTrue()).thenReturn(List.of(dairyPromo));

        CheckoutCommand command = new CheckoutCommand(
                List.of(new ItemCommand(1L, 1)),
                "CASH"
        );

        CheckoutResult result = checkoutService.calculatePreview(command);

        assertEquals(new BigDecimal("10.00"), result.getSubtotal());
        assertEquals(new BigDecimal("1.00"), result.getTotalDiscount());
        assertEquals(new BigDecimal("9.00"), result.getTotal());
    }

    @Test
    void shouldCombineMultipleDiscounts() {
        Product dairy = new Product(1L, "WMT-001", "Milk", new BigDecimal("100.00"), "Dairy", null);
        PaymentMethod debit = new PaymentMethod(1L, "DEBIT", "Debit Card", new BigDecimal("10"), null, true);

        Promotion dairyPromo = new Promotion();
        dairyPromo.setCode("DAIRY10");
        dairyPromo.setName("10% Off Dairy");
        dairyPromo.setType(Promotion.PromotionType.PERCENTAGE);
        dairyPromo.setDiscountValue(new BigDecimal("10"));
        dairyPromo.setApplicableCategory("Dairy");
        dairyPromo.setActive(true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(dairy));
        when(paymentMethodRepository.findByCodeAndActiveTrue("DEBIT")).thenReturn(Optional.of(debit));
        when(promotionRepository.findByActiveTrue()).thenReturn(List.of(dairyPromo));

        CheckoutCommand command = new CheckoutCommand(
                List.of(new ItemCommand(1L, 1)),
                "DEBIT"
        );

        CheckoutResult result = checkoutService.calculatePreview(command);

        assertEquals(new BigDecimal("100.00"), result.getSubtotal());
        assertEquals(new BigDecimal("20.00"), result.getTotalDiscount());
        assertEquals(new BigDecimal("80.00"), result.getTotal());
        assertEquals(2, result.getDiscounts().size());
    }

    @Test
    void shouldGenerateConfirmationCodeOnConfirm() {
        Product product = new Product(1L, "WMT-001", "Product", new BigDecimal("10.00"), "General", null);
        PaymentMethod cash = new PaymentMethod(1L, "CASH", "Cash", BigDecimal.ZERO, null, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(paymentMethodRepository.findByCodeAndActiveTrue("CASH")).thenReturn(Optional.of(cash));
        when(promotionRepository.findByActiveTrue()).thenReturn(Collections.emptyList());

        CheckoutCommand command = new CheckoutCommand(
                List.of(new ItemCommand(1L, 1)),
                "CASH"
        );

        CheckoutResult result = checkoutService.confirmCheckout(command);

        assertEquals("CONFIRMED", result.getStatus());
        assertNotNull(result.getConfirmationCode());
        assertTrue(result.getConfirmationCode().startsWith("WMT-"));
    }
}
