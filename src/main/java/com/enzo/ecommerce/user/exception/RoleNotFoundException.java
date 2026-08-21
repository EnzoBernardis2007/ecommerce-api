package com.enzo.ecommerce.user.exception;

import com.enzo.ecommerce.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(UUID id) {
        super("Role", "id", id);
    }

    public RoleNotFoundException(String fieldName, Object fieldValue) {
        super("Role", fieldName, fieldValue);
    }

    public RoleNotFoundException(String customMessage) {
        super(customMessage);
    }
}
