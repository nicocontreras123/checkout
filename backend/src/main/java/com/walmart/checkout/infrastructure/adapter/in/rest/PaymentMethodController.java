package com.walmart.checkout.infrastructure.adapter.in.rest;

import com.walmart.checkout.domain.model.PaymentMethod;
import com.walmart.checkout.domain.port.in.PaymentMethodQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adapter In - REST controller for payment method queries
 */
@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin(origins = "*")
@Tag(name = "Métodos de Pago", description = "API para consultar los métodos de pago disponibles y sus descuentos asociados")
public class PaymentMethodController {

    private final PaymentMethodQueryUseCase paymentMethodQueryUseCase;

    public PaymentMethodController(PaymentMethodQueryUseCase paymentMethodQueryUseCase) {
        this.paymentMethodQueryUseCase = paymentMethodQueryUseCase;
    }

    @Operation(
            summary = "Obtener métodos de pago activos",
            description = "Retorna la lista de métodos de pago disponibles actualmente, incluyendo información sobre descuentos aplicables"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de métodos de pago obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentMethod.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getActivePaymentMethods() {
        return ResponseEntity.ok(paymentMethodQueryUseCase.findAllActive());
    }
}
