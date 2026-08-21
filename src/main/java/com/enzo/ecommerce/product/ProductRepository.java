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

    @Query(
            value = """
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
            LEFT JOIN p.images i ON (i.primaryImage = true)
            """,
            countQuery = "SELECT COUNT(p) FROM Product p"
    )
    Page<ProductSummaryResponse> findAllSummary(Pageable pageable);

    // The two queries are separated to avoid the N+1 problem without triggering the MultipleBagFetchException.
    @EntityGraph(attributePaths = {
            "brand",
            "productCategories.category",
            "images"
    })
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithBaseDetails(@Param("id") UUID id);

    @EntityGraph(attributePaths = {
            "variants.attributeValues.attributeValue.attribute",
            "variants.inventory"
    })
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> fetchVariantsWithDetailsByProductId(@Param("id") UUID id);
}