package com.enzo.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "inventories",
    indexes = {
        @Index(
            name = "idx_inventories_variant_id",
            columnList = "variant_id"
        ),
        @Index(
            name = "idx_inventories_quantity",
            columnList = "quantity"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_inventories_variant",
            columnNames = "variant_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        columnDefinition = "BINARY(16)",
        nullable = false,
        updatable = false
    )
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "variant_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_inventories_variant"
        )
    )
    private ProductVariant variant;

    @Column(
        name = "quantity",
        nullable = false
    )
    private Integer quantity = 0;

    @Column(
        name = "reserved_quantity",
        nullable = false
    )
    private Integer reservedQuantity = 0;

    @Column(
        name = "reorder_level",
        nullable = false
    )
    private Integer reorderLevel = 0;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    @Column(
        name = "updated_at",
        nullable = false
    )
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}