package com.enzo.ecommerce.product.dto;

import java.util.UUID;

public record AttributeValueResponse(
        UUID id,
        String value
) {
}