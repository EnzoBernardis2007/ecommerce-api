package com.enzo.ecommerce.product.entity;

import com.enzo.ecommerce.product.embedabble.ProductVariantAttributeValueId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "product_variants",
        indexes = {
                @Index(
                        name = "idx_product_variants_product_id",
                        columnList = "product_id"
                ),
                @Index(
                        name = "idx_product_variants_active",
                        columnList = "active"
                ),
                @Index(
                        name = "idx_product_variants_barcode",
                        columnList = "barcode"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_product_variants_sku",
                        columnNames = "sku"
                ),
                @UniqueConstraint(
                        name = "uq_product_variants_barcode",
                        columnNames = "barcode"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_product_variants_product"
            )
    )
    private Product product;

    @Column(
            name = "sku",
            nullable = false,
            length = 100
    )
    private String sku;

    @Column(
            name = "price",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal price;

    @Column(
            name = "compare_at_price",
            precision = 19,
            scale = 4
    )
    private BigDecimal compareAtPrice;

    @Column(
            name = "barcode",
            length = 100
    )
    private String barcode;

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active = true;

    @OneToOne(
            mappedBy = "variant",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Inventory inventory;

    @OneToMany(
            mappedBy = "variant",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<ProductVariantAttributeValue> attributeValues =
            new HashSet<>();

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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;

        if (inventory != null && inventory.getVariant() != this) {
            inventory.setVariant(this);
        }
    }

    public void addAttributeValue(AttributeValue attributeValue) {
        ProductVariantAttributeValue relation =
                new ProductVariantAttributeValue();

        relation.setId(
                new ProductVariantAttributeValueId(
                        this.id,
                        attributeValue.getId()
                )
        );

        relation.setVariant(this);
        relation.setAttributeValue(attributeValue);

        attributeValues.add(relation);
    }

    public void removeAttributeValue(AttributeValue attributeValue) {
        attributeValues.removeIf(
                relation ->
                        relation.getAttributeValue().equals(attributeValue)
        );
    }
}