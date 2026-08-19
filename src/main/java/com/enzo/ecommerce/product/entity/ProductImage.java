package com.enzo.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "product_images",
    indexes = {
        @Index(
            name = "idx_product_images_product_id",
            columnList = "product_id"
        ),
        @Index(
            name = "idx_product_images_primary",
            columnList = "product_id, primary_image"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_product_images_order",
            columnNames = {"product_id", "display_order"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        columnDefinition = "BINARY(16)",
        nullable = false,
        updatable = false
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_product_images_product"
        )
    )
    private Product product;

    @Column(
        name = "url",
        nullable = false,
        length = 1000
    )
    private String url;

    @Column(
        name = "alt_text",
        length = 255
    )
    private String altText;

    @Column(
        name = "display_order",
        nullable = false
    )
    private Integer displayOrder = 0;

    @Column(
        name = "primary_image",
        nullable = false
    )
    private boolean primaryImage = false;

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