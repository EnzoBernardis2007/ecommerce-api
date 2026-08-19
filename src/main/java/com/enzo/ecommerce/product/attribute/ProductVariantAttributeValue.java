package com.enzo.ecommerce.product.attribute;

import com.enzo.ecommerce.product.variant.ProductVariant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "product_variant_attribute_values",
    indexes = {
        @Index(
            name = "idx_variant_attribute_values_attribute_value_id",
            columnList = "attribute_value_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductVariantAttributeValue {

    @EmbeddedId
    private ProductVariantAttributeValueId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantId")
    @JoinColumn(
        name = "variant_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_variant_attribute_values_variant"
        )
    )
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("attributeValueId")
    @JoinColumn(
        name = "attribute_value_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_variant_attribute_values_attribute_value"
        )
    )
    private AttributeValue attributeValue;

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