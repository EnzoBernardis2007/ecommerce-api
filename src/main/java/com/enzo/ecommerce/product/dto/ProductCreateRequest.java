package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductCreateRequest(
        @NotNull
        UUID brandId,

        @NotBlank
        @Size(max = 200)
        String name,

        @NotBlank
        @Size(max = 220)
        String slug,

        @Size(max = 500)
        String shortDescription,

        String description,

        ProductStatus status,

        @NotNull
        @PositiveOrZero
        BigDecimal basePrice,

        @PositiveOrZero
        BigDecimal costPrice,

        @PositiveOrZero
        Integer weightGrams,

        @PositiveOrZero
        BigDecimal lengthCm,

        @PositiveOrZero
        BigDecimal widthCm,

        @PositiveOrZero
        BigDecimal heightCm,

        Boolean active,

        List<UUID> categoryIds
) {
}
