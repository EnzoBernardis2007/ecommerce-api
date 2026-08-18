package com.enzo.ecommerce.user.repository;

import com.enzo.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {
            "roles",
            "roles.role"
    })
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}