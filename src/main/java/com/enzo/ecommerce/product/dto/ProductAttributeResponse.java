package com.enzo.ecommerce.product.dto;

import java.util.List;
import java.util.UUID;

public record ProductAttributeResponse(
        UUID id,
        String name,
        List<AttributeValueResponse> values
) {
}