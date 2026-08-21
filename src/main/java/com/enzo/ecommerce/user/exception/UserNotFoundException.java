package com.enzo.ecommerce.user.exception;

import com.enzo.ecommerce.shared.exception.ConflictException;

public class UserNotFoundException extends ConflictException {

    public UserNotFoundException(String email) {
        super("User", "email", email);
    }
}