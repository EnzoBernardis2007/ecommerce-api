package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSummaryResponse(
        UUID id,
        String name,
        String slug,
        BigDecimal price,
        ProductStatus status,
        String brandName,
        String thumbnailUrl
) {
}