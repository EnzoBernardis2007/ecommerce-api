package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.entity.ProductImage;

import java.util.UUID;

public record ProductImageResponse(
        UUID id,
        String url,
        String altText,
        Integer position
) {
    public static ProductImageResponse from(ProductImage productImage) {
        return new ProductImageResponse(
                productImage.getId(),
                productImage.getUrl(),
                productImage.getAltText(),
                productImage.getDisplayOrder()
        );
    }
}