CREATE TABLE attribute_values (
    id BINARY(16) NOT NULL,
    attribute_id BINARY(16) NOT NULL,

    value VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_attribute_values
        PRIMARY KEY (id),

    CONSTRAINT uq_attribute_values_attribute_value
        UNIQUE (attribute_id, value),

    CONSTRAINT uq_attribute_values_attribute_slug
        UNIQUE (attribute_id, slug),

    CONSTRAINT fk_attribute_values_attribute
        FOREIGN KEY (attribute_id)
        REFERENCES product_attributes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_attribute_values_attribute_id
    ON attribute_values(attribute_id);

CREATE INDEX idx_attribute_values_active
    ON attribute_values(active);