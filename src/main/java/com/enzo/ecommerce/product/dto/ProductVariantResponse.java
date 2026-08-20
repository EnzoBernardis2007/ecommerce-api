package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.entity.ProductVariant;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductVariantResponse(
        UUID id,
        String sku,
        BigDecimal price,
        boolean active,
        List<VariantAttributeResponse> attributes,
        InventoryResponse inventory
) {

    public static ProductVariantResponse from(ProductVariant productVariant) {
        return new ProductVariantResponse(
                productVariant.getId(),
                productVariant.getSku(),
                productVariant.getPrice(),
                productVariant.isActive(),

                productVariant.getAttributeValues()
                        .stream()
                        .map(VariantAttributeResponse::from)
                        .toList(),

                InventoryResponse.from(productVariant.getInventory())
        );
    }
}