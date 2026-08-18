CREATE TABLE categories (
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    parent_id BINARY(16),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_categories
        PRIMARY KEY (id),

    CONSTRAINT uq_categories_name
        UNIQUE (name),

    CONSTRAINT uq_categories_slug
        UNIQUE (slug),

    CONSTRAINT fk_categories_parent
        FOREIGN KEY (parent_id)
        REFERENCES categories(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE INDEX idx_categories_parent_id
    ON categories(parent_id);

CREATE INDEX idx_categories_active
    ON categories(active);

CREATE INDEX idx_categories_slug
    ON categories(slug);
