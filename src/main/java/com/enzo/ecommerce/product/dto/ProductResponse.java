package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProductResponse (
        UUID id,
        String name,
        String slug,
        String description,
        BigDecimal price,
        ProductStatus status,
        BrandResponse brand,
        List<CategoryResponse> categories,
        List<ProductImageResponse> images,
        List<ProductAttributeResponse> attributes,
        List<ProductVariantResponse> variants,
        Instant createdAt,
        Instant updatedAt
) {
}