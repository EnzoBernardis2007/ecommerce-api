package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.entity.Brand;

import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name,
        String slug
) {
    public static BrandResponse from(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getSlug()
        );
    }
}