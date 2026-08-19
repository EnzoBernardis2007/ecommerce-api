package com.enzo.ecommerce.product.dto;

public record InventoryResponse(
        Integer quantity,
        Integer reservedQuantity,
        Integer availableQuantity
) {
}