package com.enzo.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "brands",
    indexes = {
        @Index(name = "idx_brands_active", columnList = "active"),
        @Index(name = "idx_brands_slug", columnList = "slug")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_brands_name",
            columnNames = "name"
        ),
        @UniqueConstraint(
            name = "uq_brands_slug",
            columnNames = "slug"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Brand {

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
        name = "description",
        length = 500
    )
    private String description;

    @Column(
        name = "website_url",
        length = 500
    )
    private String websiteUrl;

    @Column(
        name = "logo_url",
        length = 500
    )
    private String logoUrl;

    @Column(
        name = "active",
        nullable = false
    )
    private boolean active = true;

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