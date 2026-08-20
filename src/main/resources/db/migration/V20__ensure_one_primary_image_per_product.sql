CREATE UNIQUE INDEX uq_product_images_one_primary
    ON product_images (
                       product_id,
        (CASE WHEN primary_image = TRUE THEN 1 ELSE NULL END)
        );