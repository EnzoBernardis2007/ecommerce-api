package com.enzo.ecommerce.users.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class UserRoleId implements Serializable {

    private UUID userId;

    private UUID roleId;

    protected UserRoleId() {
    }

    public UserRoleId(UUID userId, UUID roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId that)) return false;

        return userId.equals(that.userId)
                && roleId.equals(that.roleId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + roleId.hashCode();
    }
}