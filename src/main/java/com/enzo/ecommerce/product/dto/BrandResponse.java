package com.enzo.ecommerce.product.dto;

import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name,
        String slug
) {
}