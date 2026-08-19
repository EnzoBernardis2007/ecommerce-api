package com.enzo.ecommerce.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductVariantResponse(
        UUID id,
        String sku,
        BigDecimal price,
        Boolean active,
        List<VariantAttributeResponse> attributes,
        InventoryResponse inventory
) {
}