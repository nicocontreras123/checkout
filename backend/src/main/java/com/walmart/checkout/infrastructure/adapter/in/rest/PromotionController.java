package com.walmart.checkout.infrastructure.adapter.in.rest;

import com.walmart.checkout.domain.model.Promotion;
import com.walmart.checkout.domain.port.in.PromotionQueryUseCase;
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
 * Adapter In - REST controller for promotion queries
 */
@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*")
@Tag(name = "Promociones", description = "API para consultar las promociones activas y vigentes")
public class PromotionController {

    private final PromotionQueryUseCase promotionQueryUseCase;

    public PromotionController(PromotionQueryUseCase promotionQueryUseCase) {
        this.promotionQueryUseCase = promotionQueryUseCase;
    }

    @Operation(
            summary = "Obtener promociones activas",
            description = "Retorna la lista de promociones vigentes y activas que pueden aplicarse a productos específicos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de promociones obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Promotion.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<Promotion>> getActivePromotions() {
        return ResponseEntity.ok(promotionQueryUseCase.findAllActiveAndValid());
    }
}
