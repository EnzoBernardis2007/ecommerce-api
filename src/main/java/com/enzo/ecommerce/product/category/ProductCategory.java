package com.enzo.ecommerce.product.category;

import com.enzo.ecommerce.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "product_categories",
    indexes = {
        @Index(
            name = "idx_product_categories_category_id",
            columnList = "category_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_product_categories_product"
        )
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(
        name = "category_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_product_categories_category"
        )
    )
    private Category category;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}