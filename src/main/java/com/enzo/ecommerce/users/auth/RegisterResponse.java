package com.enzo.ecommerce.users.auth;

import java.util.UUID;

public record RegisterResponse(UUID id, String username, String email) {
}
