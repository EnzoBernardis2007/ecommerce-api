package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.entity.ProductVariantAttributeValue;

import java.util.UUID;

public record VariantAttributeResponse(
        UUID attributeId,
        String attributeName,
        UUID valueId,
        String value
) {

    public static VariantAttributeResponse from(
            ProductVariantAttributeValue productVariantAttributeValue
    ) {
        var attributeValue = productVariantAttributeValue.getAttributeValue();
        var attribute = attributeValue.getAttribute();

        return new VariantAttributeResponse(
                attribute.getId(),
                attribute.getName(),
                attributeValue.getId(),
                attributeValue.getValue()
        );
    }
}