package com.enzo.ecommerce.product.dto;

import com.enzo.ecommerce.product.ProductStatus;
import com.enzo.ecommerce.product.entity.Product;
import com.enzo.ecommerce.product.entity.ProductCategory;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String slug,
        String shortDescription,
        String description,
        ProductStatus status,
        BigDecimal basePrice,
        BigDecimal costPrice,
        Integer weightGrams,
        BigDecimal lengthCm,
        BigDecimal widthCm,
        BigDecimal heightCm,
        boolean active,
        BrandResponse brand,
        List<CategoryResponse> categories,
        List<ProductImageResponse> images,
        List<ProductVariantResponse> variants,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                product.getDescription(),
                product.getStatus(),
                product.getBasePrice(),
                product.getCostPrice(),
                product.getWeightGrams(),
                product.getLengthCm(),
                product.getWidthCm(),
                product.getHeightCm(),
                product.isActive(),

                product.getBrand() != null
                        ? BrandResponse.from(product.getBrand())
                        : null,

                product.getProductCategories()
                        .stream()
                        .map(ProductCategory::getCategory)
                        .map(CategoryResponse::from)
                        .toList(),

                product.getImages()
                        .stream()
                        .map(ProductImageResponse::from)
                        .toList(),

                product.getVariants()
                        .stream()
                        .map(ProductVariantResponse::from)
                        .toList(),

                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}