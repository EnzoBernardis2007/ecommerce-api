package com.enzo.ecommerce.product.entity;

import com.enzo.ecommerce.product.ProductStatus;
import com.enzo.ecommerce.product.brand.Brand;
import com.enzo.ecommerce.product.category.Category;
import com.enzo.ecommerce.product.category.ProductCategory;
import com.enzo.ecommerce.product.category.ProductCategoryId;
import com.enzo.ecommerce.product.image.ProductImage;
import com.enzo.ecommerce.product.variant.ProductVariant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_products_brand_id", columnList = "brand_id"),
                @Index(name = "idx_products_status", columnList = "status"),
                @Index(name = "idx_products_active", columnList = "active"),
                @Index(name = "idx_products_created_at", columnList = "created_at"),
                @Index(name = "idx_products_name", columnList = "name")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_products_slug",
                        columnNames = "slug"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Product {

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
            name = "brand_id",
            foreignKey = @ForeignKey(
                    name = "fk_products_brand"
            )
    )
    private Brand brand;

    @Column(
            name = "name",
            nullable = false,
            length = 200
    )
    private String name;

    @Column(
            name = "slug",
            nullable = false,
            length = 220
    )
    private String slug;

    @Column(
            name = "short_description",
            length = 500
    )
    private String shortDescription;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(
            name = "base_price",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal basePrice;

    @Column(
            name = "cost_price",
            precision = 19,
            scale = 4
    )
    private BigDecimal costPrice;

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Column(
            name = "length_cm",
            precision = 10,
            scale = 2
    )
    private BigDecimal lengthCm;

    @Column(
            name = "width_cm",
            precision = 10,
            scale = 2
    )
    private BigDecimal widthCm;

    @Column(
            name = "height_cm",
            precision = 10,
            scale = 2
    )
    private BigDecimal heightCm;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active = true;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductCategory> productCategories = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("displayOrder ASC")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductVariant> variants = new ArrayList<>();

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

    public void addCategory(Category category) {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setId(
                new ProductCategoryId(
                        this.id,
                        category.getId()
                )
        );

        productCategory.setProduct(this);
        productCategory.setCategory(category);

        productCategories.add(productCategory);
    }

    public void removeCategory(Category category) {
        productCategories.removeIf(
                productCategory ->
                        productCategory.getCategory().equals(category)
        );
    }

    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }

    public void addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProduct(this);
    }

    public void removeVariant(ProductVariant variant) {
        variants.remove(variant);
        variant.setProduct(null);
    }
}