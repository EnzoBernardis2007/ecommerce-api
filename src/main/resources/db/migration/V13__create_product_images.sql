CREATE TABLE product_images (
    id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,

    url VARCHAR(1000) NOT NULL,
    alt_text VARCHAR(255),

    display_order INT UNSIGNED NOT NULL DEFAULT 0,
    primary_image BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_product_images
        PRIMARY KEY (id),

    CONSTRAINT fk_product_images_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT uq_product_images_order
        UNIQUE (product_id, display_order)
);

CREATE INDEX idx_product_images_product_id
    ON product_images(product_id);

CREATE INDEX idx_product_images_primary
    ON product_images(product_id, primary_image);