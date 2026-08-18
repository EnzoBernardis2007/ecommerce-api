package com.enzo.ecommerce.user.dto;

import java.util.Set;
import java.util.UUID;

public record MeResponse(
        UUID id,
        String email,
        String displayName,
        boolean active,
        Set<String> roles
) {
}