package com.enzo.ecommerce.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_roles_name",
                        columnNames = "name"
                )
        }
)
public class Role {

    @Id
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "BINARY(16)"
    )
    private UUID id;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "description",
            length = 255
    )
    private String description;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    protected Role() {
    }

    public Role(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();

        if (id == null) {
            id = UUID.randomUUID();
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // getters e setters
}