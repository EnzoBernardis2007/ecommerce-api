package com.enzo.ecommerce.product.dto;

import java.util.UUID;

public record VariantAttributeResponse(
        UUID attributeId,
        String attributeName,
        UUID valueId,
        String value
) {
}