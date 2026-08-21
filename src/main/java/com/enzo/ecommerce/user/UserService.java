package com.enzo.ecommerce.user;

import com.enzo.ecommerce.user.dto.MeResponse;
import com.enzo.ecommerce.user.dto.UserCreatedDto;
import com.enzo.ecommerce.user.entity.Role;
import com.enzo.ecommerce.user.entity.User;
import com.enzo.ecommerce.user.exception.EmailAlreadyExistsException;
import com.enzo.ecommerce.user.exception.RoleNotFoundException;
import com.enzo.ecommerce.user.exception.UserNotFoundException;
import com.enzo.ecommerce.user.repository.RoleRepository;
import com.enzo.ecommerce.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserCreatedDto createCustomer(String email, String rawPassword, String displayName) throws Exception {
        if (userRepository.existsByEmailAndActiveTrueAndDeletedAtIsNull(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RoleNotFoundException("name", "CUSTOMER"));

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(email, hashedPassword, displayName);
        user.addRole(customerRole);

        User savedUser = userRepository.save(user);

        return new UserCreatedDto(
                savedUser.getId(),
                savedUser.getDisplayname(),
                savedUser.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public MeResponse findMe(String email) throws Exception {
        User user = userRepository.findByEmailAndActiveTrueAndDeletedAtIsNull(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        var roles = user.getRoles()
                .stream()
                .map(userRole -> userRole.getRole().getName())
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