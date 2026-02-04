package com.walmart.checkout.infrastructure.adapter.in.rest;

import com.walmart.checkout.domain.model.Product;
import com.walmart.checkout.domain.port.in.ProductQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adapter In - REST controller for product queries
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API para consultar el catálogo de productos disponibles")
public class ProductController {

    private final ProductQueryUseCase productQueryUseCase;

    public ProductController(ProductQueryUseCase productQueryUseCase) {
        this.productQueryUseCase = productQueryUseCase;
    }

    @Operation(
            summary = "Obtener todos los productos",
            description = "Retorna la lista completa de productos disponibles en el catálogo de Walmart Chile"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productQueryUseCase.findAll());
    }

    @Operation(
            summary = "Obtener producto por ID",
            description = "Retorna los detalles de un producto específico mediante su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            ),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "ID del producto a consultar", required = true, example = "1")
            @PathVariable Long id) {
        return productQueryUseCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Buscar productos por categoría",
            description = "Retorna todos los productos que pertenecen a una categoría específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos de la categoría obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            )
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Nombre de la categoría", required = true, example = "Electrónica")
            @PathVariable String category) {
        return ResponseEntity.ok(productQueryUseCase.findByCategory(category));
    }
}
