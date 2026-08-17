package com.enzo.ecommerce.users.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}