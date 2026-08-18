CREATE TABLE product_categories (
    product_id BINARY(16) NOT NULL,
    category_id BINARY(16) NOT NULL,

    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_product_categories
        PRIMARY KEY (product_id, category_id),

    CONSTRAINT fk_product_categories_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_product_categories_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_product_categories_category_id
    ON product_categories(category_id);