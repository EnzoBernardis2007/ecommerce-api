package com.enzo.ecommerce.user.exception;

import com.enzo.ecommerce.shared.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String email) {
        super("User", "email", email);
    }
}