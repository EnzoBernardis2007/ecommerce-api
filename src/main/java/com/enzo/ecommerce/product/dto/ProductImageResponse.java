package com.enzo.ecommerce.product.dto;

import java.util.UUID;

public record ProductImageResponse(
        UUID id,
        String url,
        String altText,
        Integer position
) {
}