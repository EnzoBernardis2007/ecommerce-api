-- ============================================================
-- V19 - Seed product catalog
-- ============================================================

-- ============================================================
-- BRANDS
-- ============================================================

SET @samsung_id = UUID_TO_BIN(UUID());
SET @apple_id = UUID_TO_BIN(UUID());

INSERT INTO brands (
    id,
    name,
    slug,
    description,
    website_url,
    logo_url,
    active
)
VALUES
    (
        @samsung_id,
        'Samsung',
        'samsung',
        'Samsung consumer electronics brand.',
        'https://www.samsung.com',
        'https://example.com/images/brands/samsung.png',
        TRUE
    ),
    (
        @apple_id,
        'Apple',
        'apple',
        'Apple consumer electronics brand.',
        'https://www.apple.com',
        'https://example.com/images/brands/apple.png',
        TRUE
    );


-- ============================================================
-- CATEGORIES
-- ============================================================

SET @smartphones_category_id = UUID_TO_BIN(UUID());
SET @cell_phones_category_id = UUID_TO_BIN(UUID());

INSERT INTO categories (
    id,
    name,
    slug,
    description,
    parent_id,
    active
)
VALUES
    (
        @smartphones_category_id,
        'Smartphones',
        'smartphones',
        'Smartphones and mobile devices.',
        NULL,
        TRUE
    ),
    (
        @cell_phones_category_id,
        'Cell Phones',
        'cell-phones',
        'Mobile phones and cellular devices.',
        NULL,
        TRUE
    );


-- ============================================================
-- PRODUCTS
-- ============================================================

SET @galaxy_s25_id = UUID_TO_BIN(UUID());
SET @iphone_16_id = UUID_TO_BIN(UUID());

INSERT INTO products (
    id,
    brand_id,
    name,
    slug,
    short_description,
    description,
    status,
    base_price,
    cost_price,
    weight_grams,
    length_cm,
    width_cm,
    height_cm,
    active
)
VALUES
    (
        @galaxy_s25_id,
        @samsung_id,
        'Samsung Galaxy S25',
        'samsung-galaxy-s25',
        'Samsung Galaxy S25 smartphone.',
        'The Samsung Galaxy S25 is a high-performance smartphone with a modern design, advanced cameras, and multiple storage and color options.',
        'ACTIVE',
        6999.9000,
        4800.0000,
        162,
        14.68,
        7.05,
        0.72,
        TRUE
    ),
    (
        @iphone_16_id,
        @apple_id,
        'Apple iPhone 16',
        'apple-iphone-16',
        'Apple iPhone 16 smartphone.',
        'The Apple iPhone 16 is a high-performance smartphone with advanced cameras, a modern design, and multiple storage and color options.',
        'ACTIVE',
        7999.9000,
        5600.0000,
        170,
        14.76,
        7.16,
        0.78,
        TRUE
    );


-- ============================================================
-- PRODUCT CATEGORIES
-- ============================================================

INSERT INTO product_categories (
    product_id,
    category_id
)
VALUES
    (
        @galaxy_s25_id,
        @smartphones_category_id
    ),
    (
        @galaxy_s25_id,
        @cell_phones_category_id
    ),
    (
        @iphone_16_id,
        @smartphones_category_id
    ),
    (
        @iphone_16_id,
        @cell_phones_category_id
    );


-- ============================================================
-- PRODUCT IMAGES
-- ============================================================

SET @galaxy_s25_image_1_id = UUID_TO_BIN(UUID());
SET @galaxy_s25_image_2_id = UUID_TO_BIN(UUID());

SET @iphone_16_image_1_id = UUID_TO_BIN(UUID());
SET @iphone_16_image_2_id = UUID_TO_BIN(UUID());

INSERT INTO product_images (
    id,
    product_id,
    url,
    alt_text,
    display_order,
    primary_image
)
VALUES
    (
        @galaxy_s25_image_1_id,
        @galaxy_s25_id,
        'https://example.com/images/products/samsung-galaxy-s25-front.jpg',
        'Samsung Galaxy S25 front view',
        0,
        TRUE
    ),
    (
        @galaxy_s25_image_2_id,
        @galaxy_s25_id,
        'https://example.com/images/products/samsung-galaxy-s25-back.jpg',
        'Samsung Galaxy S25 back view',
        1,
        FALSE
    ),
    (
        @iphone_16_image_1_id,
        @iphone_16_id,
        'https://example.com/images/products/apple-iphone-16-front.jpg',
        'Apple iPhone 16 front view',
        0,
        TRUE
    ),
    (
        @iphone_16_image_2_id,
        @iphone_16_id,
        'https://example.com/images/products/apple-iphone-16-back.jpg',
        'Apple iPhone 16 back view',
        1,
        FALSE
    );


-- ============================================================
-- PRODUCT VARIANTS
-- ============================================================

-- Samsung Galaxy S25

SET @s25_128_black_id = UUID_TO_BIN(UUID());
SET @s25_256_black_id = UUID_TO_BIN(UUID());
SET @s25_128_blue_id = UUID_TO_BIN(UUID());

INSERT INTO product_variants (
    id,
    product_id,
    sku,
    price,
    compare_at_price,
    barcode,
    weight_grams,
    active
)
VALUES
    (
        @s25_128_black_id,
        @galaxy_s25_id,
        'S25-128-BLK',
        6999.9000,
        7499.9000,
        '789000000001',
        162,
        TRUE
    ),
    (
        @s25_256_black_id,
        @galaxy_s25_id,
        'S25-256-BLK',
        7499.9000,
        7999.9000,
        '789000000002',
        162,
        TRUE
    ),
    (
        @s25_128_blue_id,
        @galaxy_s25_id,
        'S25-128-BLU',
        6999.9000,
        7499.9000,
        '789000000003',
        162,
        TRUE
    );


-- Apple iPhone 16

SET @iphone_16_128_black_id = UUID_TO_BIN(UUID());
SET @iphone_16_256_black_id = UUID_TO_BIN(UUID());
SET @iphone_16_128_blue_id = UUID_TO_BIN(UUID());

INSERT INTO product_variants (
    id,
    product_id,
    sku,
    price,
    compare_at_price,
    barcode,
    weight_grams,
    active
)
VALUES
    (
        @iphone_16_128_black_id,
        @iphone_16_id,
        'IP16-128-BLK',
        7999.9000,
        8499.9000,
        '789000000004',
        170,
        TRUE
    ),
    (
        @iphone_16_256_black_id,
        @iphone_16_id,
        'IP16-256-BLK',
        8999.9000,
        9499.9000,
        '789000000005',
        170,
        TRUE
    ),
    (
        @iphone_16_128_blue_id,
        @iphone_16_id,
        'IP16-128-BLU',
        7999.9000,
        8499.9000,
        '789000000006',
        170,
        TRUE
    );


-- ============================================================
-- PRODUCT ATTRIBUTES
-- ============================================================

-- These attributes are global.
-- They are not directly associated with a product.

SET @storage_attribute_id = UUID_TO_BIN(UUID());
SET @color_attribute_id = UUID_TO_BIN(UUID());

INSERT INTO product_attributes (
    id,
    name,
    slug,
    active
)
VALUES
    (
        @storage_attribute_id,
        'Storage',
        'storage',
        TRUE
    ),
    (
        @color_attribute_id,
        'Color',
        'color',
        TRUE
    );


-- ============================================================
-- ATTRIBUTE VALUES
-- ============================================================

SET @storage_128gb_id = UUID_TO_BIN(UUID());
SET @storage_256gb_id = UUID_TO_BIN(UUID());

SET @color_black_id = UUID_TO_BIN(UUID());
SET @color_blue_id = UUID_TO_BIN(UUID());

INSERT INTO attribute_values (
    id,
    attribute_id,
    value,
    slug,
    active
)
VALUES
    (
        @storage_128gb_id,
        @storage_attribute_id,
        '128GB',
        '128gb',
        TRUE
    ),
    (
        @storage_256gb_id,
        @storage_attribute_id,
        '256GB',
        '256gb',
        TRUE
    ),
    (
        @color_black_id,
        @color_attribute_id,
        'Black',
        'black',
        TRUE
    ),
    (
        @color_blue_id,
        @color_attribute_id,
        'Blue',
        'blue',
        TRUE
    );


-- ============================================================
-- VARIANT ATTRIBUTE VALUES
-- ============================================================

-- Samsung Galaxy S25
-- 128GB / Black

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @s25_128_black_id,
        @storage_128gb_id
    ),
    (
        @s25_128_black_id,
        @color_black_id
    );


-- Samsung Galaxy S25
-- 256GB / Black

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @s25_256_black_id,
        @storage_256gb_id
    ),
    (
        @s25_256_black_id,
        @color_black_id
    );


-- Samsung Galaxy S25
-- 128GB / Blue

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @s25_128_blue_id,
        @storage_128gb_id
    ),
    (
        @s25_128_blue_id,
        @color_blue_id
    );


-- Apple iPhone 16
-- 128GB / Black

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @iphone_16_128_black_id,
        @storage_128gb_id
    ),
    (
        @iphone_16_128_black_id,
        @color_black_id
    );


-- Apple iPhone 16
-- 256GB / Black

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @iphone_16_256_black_id,
        @storage_256gb_id
    ),
    (
        @iphone_16_256_black_id,
        @color_black_id
    );


-- Apple iPhone 16
-- 128GB / Blue

INSERT INTO product_variant_attribute_values (
    variant_id,
    attribute_value_id
)
VALUES
    (
        @iphone_16_128_blue_id,
        @storage_128gb_id
    ),
    (
        @iphone_16_128_blue_id,
        @color_blue_id
    );


-- ============================================================
-- INVENTORIES
-- ============================================================

SET @s25_128_black_inventory_id = UUID_TO_BIN(UUID());
SET @s25_256_black_inventory_id = UUID_TO_BIN(UUID());
SET @s25_128_blue_inventory_id = UUID_TO_BIN(UUID());

SET @iphone_16_128_black_inventory_id = UUID_TO_BIN(UUID());
SET @iphone_16_256_black_inventory_id = UUID_TO_BIN(UUID());
SET @iphone_16_128_blue_inventory_id = UUID_TO_BIN(UUID());

INSERT INTO inventories (
    id,
    variant_id,
    quantity,
    reserved_quantity,
    reorder_level
)
VALUES
    (
        @s25_128_black_inventory_id,
        @s25_128_black_id,
        25,
        2,
        5
    ),
    (
        @s25_256_black_inventory_id,
        @s25_256_black_id,
        15,
        1,
        5
    ),
    (
        @s25_128_blue_inventory_id,
        @s25_128_blue_id,
        20,
        0,
        5
    ),
    (
        @iphone_16_128_black_inventory_id,
        @iphone_16_128_black_id,
        30,
        3,
        5
    ),
    (
        @iphone_16_256_black_inventory_id,
        @iphone_16_256_black_id,
        12,
        1,
        5
    ),
    (
        @iphone_16_128_blue_inventory_id,
        @iphone_16_128_blue_id,
        18,
        0,
        5
    );