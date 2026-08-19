package com.enzo.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Entity
@Table(
        name = "attribute_values",
        indexes = {
                @Index(
                        name = "idx_attribute_values_attribute_id",
                        columnList = "attribute_id"
                ),
                @Index(
                        name = "idx_attribute_values_active",
                        columnList = "active"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_attribute_values_attribute_value",
                        columnNames = {"attribute_id", "value"}
                ),
                @UniqueConstraint(
                        name = "uq_attribute_values_attribute_slug",
                        columnNames = {"attribute_id", "slug"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class AttributeValue {

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
            name = "attribute_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_attribute_values_attribute"
            )
    )
    private ProductAttribute attribute;

    @Column(
            name = "value",
            nullable = false,
            length = 100
    )
    private String value;

    @Column(
            name = "slug",
            nullable = false,
            length = 120
    )
    private String slug;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active = true;

    @OneToMany(
            mappedBy = "attributeValue"
    )
    private Set<ProductVariantAttributeValue> variants =
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
}