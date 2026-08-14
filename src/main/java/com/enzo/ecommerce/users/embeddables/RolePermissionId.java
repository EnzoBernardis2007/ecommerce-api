package com.enzo.ecommerce.users.embeddables;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record RolePermissionId(UUID roleId, UUID permissionId) implements Serializable {}