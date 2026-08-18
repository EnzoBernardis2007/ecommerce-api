package com.enzo.ecommerce.users.me;

import com.enzo.ecommerce.users.entities.User;
import com.enzo.ecommerce.users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class MeService {

    private final UserRepository repository;

    public MeService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MeResponse findMe(String email) throws Exception {
        User user = repository.findByEmail(email).orElseThrow(Exception::new);

        var roles = user.getRoles()
                .stream()
                .map(userRole ->
                        userRole.getRole()
                                .getName())
                .collect(Collectors.toSet());

        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getDisplayname(),
                user.isActive(),
                roles
        );
    }

}
