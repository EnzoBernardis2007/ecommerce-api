package com.enzo.ecommerce.product.category;

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
    name = "categories",
    indexes = {
        @Index(name = "idx_categories_parent_id", columnList = "parent_id"),
        @Index(name = "idx_categories_active", columnList = "active"),
        @Index(name = "idx_categories_slug", columnList = "slug")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_categories_name",
            columnNames = "name"
        ),
        @UniqueConstraint(
            name = "uq_categories_slug",
            columnNames = "slug"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Category {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_id",
        foreignKey = @ForeignKey(name = "fk_categories_parent")
    )
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

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