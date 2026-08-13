package com.enzo.ecommerce.users.entities;

import com.enzo.ecommerce.users.embeddables.UserRoleId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_user_roles_user"
            )
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("roleId")
    @JoinColumn(
            name = "role_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_user_roles_role"
            )
    )
    private Role role;

    @Column(
            name = "assigned_at",
            nullable = false,
            updatable = false
    )
    private Instant assignedAt;

    protected UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;

        this.id = new UserRoleId(
                user.getId(),
                role.getId()
        );
    }

    @PrePersist
    protected void onCreate() {
        if (assignedAt == null) {
            assignedAt = Instant.now();
        }
    }

}