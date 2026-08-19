package com.enzo.ecommerce.product;

import com.enzo.ecommerce.product.dto.ProductSummaryResponse;
import com.enzo.ecommerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
    SELECT new com.enzo.ecommerce.product.dto.ProductSummaryResponse(
        p.id,
        p.name,
        p.slug,
        p.price,
        p.status,
        b.name,
        i.url
    )
    FROM Product p
    JOIN p.brand b
    LEFT JOIN p.images i ON i.position = 0
    """)
    Page<ProductSummaryResponse> findAllSummary(Pageable pageable);

}
