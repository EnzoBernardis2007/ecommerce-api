package com.enzo.ecommerce.users.entities;

import com.enzo.ecommerce.users.embeddables.RolePermissionId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "role_permissions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("roleId")
    @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(name = "fk_role_permissions_role")
    )
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("permissionId")
    @JoinColumn(
            name = "permission_id",
            foreignKey = @ForeignKey(name = "fk_role_permissions_permission")
    )
    private Permission permission;

    @CreationTimestamp
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private Instant assignedAt;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;

        if (role.getId() != null && permission.getId() != null) {
            this.id = new RolePermissionId(role.getId(), permission.getId());
        }
    }
}