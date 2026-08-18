CREATE TABLE product_variant_attribute_values (
    variant_id BINARY(16) NOT NULL,
    attribute_value_id BINARY(16) NOT NULL,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_product_variant_attribute_values
        PRIMARY KEY (variant_id, attribute_value_id),

    CONSTRAINT fk_variant_attribute_values_variant
        FOREIGN KEY (variant_id)
        REFERENCES product_variants(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_variant_attribute_values_attribute_value
        FOREIGN KEY (attribute_value_id)
        REFERENCES attribute_values(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_variant_attribute_values_attribute_value_id
    ON product_variant_attribute_values(attribute_value_id);