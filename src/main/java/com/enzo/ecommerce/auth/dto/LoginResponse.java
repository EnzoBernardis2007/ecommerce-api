package com.enzo.ecommerce.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}