package com.walmart.checkout.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * Domain entity - Pure business object without infrastructure dependencies
 */
@Schema(description = "Producto del catálogo de Walmart Chile")
public class Product {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    
    @Schema(description = "SKU del producto", example = "ELEC-001")
    private String sku;
    
    @Schema(description = "Nombre del producto", example = "Notebook HP Pavilion")
    private String name;
    
    @Schema(description = "Precio del producto en CLP", example = "599990")
    private BigDecimal price;
    
    @Schema(description = "Categoría del producto", example = "Electrónica")
    private String category;
    
    @Schema(description = "URL de la imagen del producto", example = "https://images.walmart.cl/product-001.jpg")
    private String imageUrl;

    public Product() {}

    public Product(Long id, String sku, String name, BigDecimal price, String category, String imageUrl) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
