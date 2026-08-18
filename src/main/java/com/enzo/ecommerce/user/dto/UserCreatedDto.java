package com.enzo.ecommerce.user.dto;

import java.util.UUID;

public record UserCreatedDto(
        UUID id,
        String displayName,
        String email
) {}