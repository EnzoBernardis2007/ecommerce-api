package com.enzo.ecommerce.users.auth;

public record LogoutRequest(
        String refreshToken
) {}