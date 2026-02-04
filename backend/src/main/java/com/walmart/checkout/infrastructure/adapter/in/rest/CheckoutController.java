package com.walmart.checkout.infrastructure.adapter.in.rest;

import com.walmart.checkout.domain.model.CheckoutResult;
import com.walmart.checkout.domain.port.in.CheckoutUseCase;
import com.walmart.checkout.domain.port.in.CheckoutUseCase.CheckoutCommand;
import com.walmart.checkout.domain.port.in.CheckoutUseCase.ItemCommand;
import com.walmart.checkout.infrastructure.adapter.in.rest.dto.CheckoutRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Adapter In - REST controller for checkout operations
 */
@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
@Tag(name = "Checkout", description = "API para procesar órdenes de compra con cálculo de descuentos y confirmación")
public class CheckoutController {

    private final CheckoutUseCase checkoutUseCase;

    public CheckoutController(CheckoutUseCase checkoutUseCase) {
        this.checkoutUseCase = checkoutUseCase;
    }

    @Operation(
            summary = "Vista previa del checkout",
            description = "Calcula el total de la compra con todos los descuentos aplicables (promociones y método de pago) sin confirmar la orden. " +
                    "Útil para mostrar el resumen al usuario antes de la confirmación final."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cálculo de preview exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckoutResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida (items vacíos, productos no encontrados, etc.)",
                    content = @Content
            )
    })
    @PostMapping("/preview")
    public ResponseEntity<CheckoutResult> previewCheckout(
            @Parameter(
                    description = "Datos del carrito con productos, cantidades y método de pago seleccionado",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "items": [
                                        {"productId": 1, "quantity": 2},
                                        {"productId": 3, "quantity": 1}
                                      ],
                                      "paymentMethodCode": "CREDIT_CARD"
                                    }
                                    """
                            )
                    )
            )
            @Valid @RequestBody CheckoutRequest request) {
        CheckoutCommand command = toCommand(request);
        CheckoutResult result = checkoutUseCase.calculatePreview(command);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Confirmar checkout",
            description = "Confirma la orden de compra y genera un código de confirmación único. " +
                    "Calcula todos los descuentos aplicables y registra la transacción en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden confirmada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckoutResult.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida (items vacíos, productos no encontrados, stock insuficiente, etc.)",
                    content = @Content
            )
    })
    @PostMapping("/confirm")
    public ResponseEntity<CheckoutResult> confirmCheckout(
            @Parameter(
                    description = "Datos del carrito con productos, cantidades y método de pago seleccionado para confirmar",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "items": [
                                        {"productId": 1, "quantity": 2},
                                        {"productId": 3, "quantity": 1}
                                      ],
                                      "paymentMethodCode": "DEBIT_CARD"
                                    }
                                    """
                            )
                    )
            )
            @Valid @RequestBody CheckoutRequest request) {
        CheckoutCommand command = toCommand(request);
        CheckoutResult result = checkoutUseCase.confirmCheckout(command);
        return ResponseEntity.ok(result);
    }

    private CheckoutCommand toCommand(CheckoutRequest request) {
        return new CheckoutCommand(
                request.getItems().stream()
                        .map(item -> new ItemCommand(item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList()),
                request.getPaymentMethodCode()
        );
    }
}
