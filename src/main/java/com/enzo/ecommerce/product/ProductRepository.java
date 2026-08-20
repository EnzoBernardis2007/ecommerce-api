package com.enzo.ecommerce.product;

import com.enzo.ecommerce.product.dto.ProductSummaryResponse;
import com.enzo.ecommerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
        SELECT new com.enzo.ecommerce.product.dto.ProductSummaryResponse(
            p.id,
            p.name,
            p.slug,
            p.basePrice,
            p.status,
            b.name,
            i.url
        )
        FROM Product p
        LEFT JOIN p.brand b
        LEFT JOIN p.images i ON i.primaryImage = true
        """)
    Page<ProductSummaryResponse> findAllSummary(Pageable pageable);

    @EntityGraph(attributePaths = {
            "brand",
            "productCategories",
            "productCategories.category",
            "images",
            "variants",
            "variants.attributeValues",
            "variants.attributeValues.attributeValue",
            "variants.attributeValues.attributeValue.attribute",
            "variants.inventory"
    })
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.id = :id
        """)
    Optional<Product> findByIdWithDetails(@Param("id") UUID id);
}