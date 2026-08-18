CREATE TABLE brands (
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    website_url VARCHAR(500),
    logo_url VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_brands
        PRIMARY KEY (id),

    CONSTRAINT uq_brands_name
        UNIQUE (name),

    CONSTRAINT uq_brands_slug
        UNIQUE (slug)
);

CREATE INDEX idx_brands_active
    ON brands(active);

CREATE INDEX idx_brands_slug
    ON brands(slug);