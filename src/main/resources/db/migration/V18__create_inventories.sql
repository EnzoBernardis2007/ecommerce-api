CREATE TABLE inventories (
    id BINARY(16) NOT NULL,
    variant_id BINARY(16) NOT NULL,

    quantity INT UNSIGNED NOT NULL DEFAULT 0,
    reserved_quantity INT UNSIGNED NOT NULL DEFAULT 0,

    reorder_level INT UNSIGNED NOT NULL DEFAULT 0,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_inventories
        PRIMARY KEY (id),

    CONSTRAINT uq_inventories_variant
        UNIQUE (variant_id),

    CONSTRAINT fk_inventories_variant
        FOREIGN KEY (variant_id)
        REFERENCES product_variants(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT chk_inventories_reserved
        CHECK (reserved_quantity <= quantity)
);

CREATE INDEX idx_inventories_variant_id
    ON inventories(variant_id);

CREATE INDEX idx_inventories_quantity
    ON inventories(quantity);