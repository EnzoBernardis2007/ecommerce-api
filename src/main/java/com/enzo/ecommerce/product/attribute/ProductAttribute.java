package com.enzo.ecommerce.product.attribute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "product_attributes",
    indexes = {
        @Index(
            name = "idx_product_attributes_active",
            columnList = "active"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_product_attributes_name",
            columnNames = "name"
        ),
        @UniqueConstraint(
            name = "uq_product_attributes_slug",
            columnNames = "slug"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        columnDefinition = "BINARY(16)",
        nullable = false,
        updatable = false
    )
    private UUID id;

    @Column(
        name = "name",
        nullable = false,
        length = 100
    )
    private String name;

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
        mappedBy = "attribute",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<AttributeValue> values = new ArrayList<>();

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

    public void addValue(AttributeValue value) {
        values.add(value);
        value.setAttribute(this);
    }

    public void removeValue(AttributeValue value) {
        values.remove(value);
        value.setAttribute(null);
    }
}