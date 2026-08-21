package com.enzo.ecommerce.product.exception;

import com.enzo.ecommerce.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(UUID id) {
        super("Product", "id", id);
    }

    public ProductNotFoundException(String fieldName, Object fieldValue) {
        super("Product", fieldName, fieldValue);
    }

    public ProductNotFoundException(String customMessage) {
        super(customMessage);
    }
}