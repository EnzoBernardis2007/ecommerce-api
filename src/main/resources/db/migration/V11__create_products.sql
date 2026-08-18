CREATE TABLE products (
    id BINARY(16) NOT NULL,
    brand_id BINARY(16),

    name VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL,

    short_description VARCHAR(500),
    description TEXT,

    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',

    base_price DECIMAL(19,4) NOT NULL,
    cost_price DECIMAL(19,4),

    weight_grams INT UNSIGNED,
    length_cm DECIMAL(10,2),
    width_cm DECIMAL(10,2),
    height_cm DECIMAL(10,2),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_products
        PRIMARY KEY (id),

    CONSTRAINT uq_products_slug
        UNIQUE (slug),

    CONSTRAINT fk_products_brand
        FOREIGN KEY (brand_id)
        REFERENCES brands(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT chk_products_status
        CHECK (
            status IN (
                'DRAFT',
                'ACTIVE',
                'INACTIVE',
                'ARCHIVED'
            )
        ),

    CONSTRAINT chk_products_base_price
        CHECK (base_price >= 0),

    CONSTRAINT chk_products_cost_price
        CHECK (cost_price IS NULL OR cost_price >= 0)
);

CREATE INDEX idx_products_brand_id
    ON products(brand_id);

CREATE INDEX idx_products_status
    ON products(status);

CREATE INDEX idx_products_active
    ON products(active);

CREATE INDEX idx_products_created_at
    ON products(created_at);

CREATE INDEX idx_products_name
    ON products(name);

CREATE FULLTEXT INDEX ft_products_name_description
    ON products(name, short_description, description);
