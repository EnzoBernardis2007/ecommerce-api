CREATE TABLE product_variants (
    id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,

    sku VARCHAR(100) NOT NULL,

    price DECIMAL(19,4) NOT NULL,
    compare_at_price DECIMAL(19,4),

    barcode VARCHAR(100),

    weight_grams INT UNSIGNED,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_product_variants
        PRIMARY KEY (id),

    CONSTRAINT uq_product_variants_sku
        UNIQUE (sku),

    CONSTRAINT uq_product_variants_barcode
        UNIQUE (barcode),

    CONSTRAINT fk_product_variants_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT chk_product_variants_price
        CHECK (price >= 0),

    CONSTRAINT chk_product_variants_compare_at_price
        CHECK (
            compare_at_price IS NULL
            OR compare_at_price >= 0
        )
);

CREATE INDEX idx_product_variants_product_id
    ON product_variants(product_id);

CREATE INDEX idx_product_variants_active
    ON product_variants(active);

CREATE INDEX idx_product_variants_barcode
    ON product_variants(barcode);