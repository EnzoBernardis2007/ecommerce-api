CREATE TABLE product_attributes (
    id BINARY(16) NOT NULL,

    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_product_attributes
        PRIMARY KEY (id),

    CONSTRAINT uq_product_attributes_name
        UNIQUE (name),

    CONSTRAINT uq_product_attributes_slug
        UNIQUE (slug)
);

CREATE INDEX idx_product_attributes_active
    ON product_attributes(active);