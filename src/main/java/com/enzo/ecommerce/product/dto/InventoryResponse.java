package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.entity.Inventory;

public record InventoryResponse(
        Integer quantity,
        Integer reservedQuantity,
        Integer availableQuantity
) {

    public static InventoryResponse from(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        int quantity = inventory.getQuantity() != null
                ? inventory.getQuantity()
                : 0;

        int reservedQuantity = inventory.getReservedQuantity() != null
                ? inventory.getReservedQuantity()
                : 0;

        return new InventoryResponse(
                quantity,
                reservedQuantity,
                Math.max(0, quantity - reservedQuantity)
        );
    }
}