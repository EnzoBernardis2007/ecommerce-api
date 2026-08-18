package com.enzo.ecommerce.users.auth;

import com.enzo.ecommerce.security.JwtService;
import com.enzo.ecommerce.users.entities.RefreshToken;
import com.enzo.ecommerce.users.entities.Role;
import com.enzo.ecommerce.users.entities.User;
import com.enzo.ecommerce.users.repositories.RefreshTokenRepository;
import com.enzo.ecommerce.users.repositories.RoleRepository;
import com.enzo.ecommerce.users.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 30;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository,
            RefreshTokenGenerator refreshTokenGenerator
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest dto)
            throws Exception {

        if (userRepository.existsByEmail(dto.email())) {
            throw new Exception();
        }

        Role customerRole = roleRepository
                .findByName("CUSTOMER")
                .orElseThrow(Exception::new);

        String hashedPassword =
                passwordEncoder.encode(dto.password());

        User user = new User(
                dto.email(),
                hashedPassword,
                dto.username()
        );

        user.addRole(customerRole);

        userRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest dto)
            throws Exception {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.email(),
                                dto.password()
                        )
                );

        User user = (User) authentication.getPrincipal();

        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshTokenValue =
                refreshTokenGenerator.generate();

        RefreshToken refreshToken = new RefreshToken(
                refreshTokenValue,
                user,
                Instant.now().plus(
                        REFRESH_TOKEN_EXPIRATION_DAYS,
                        ChronoUnit.DAYS
                )
        );

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(
                accessToken,
                refreshTokenValue
        );
    }

    @Transactional
    public LoginResponse refresh(RefreshRequest dto)
            throws Exception {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(dto.refreshToken())
                        .orElseThrow(Exception::new);

        if (refreshToken.isExpired()
                || refreshToken.isRevoked()) {

            throw new Exception();
        }

        User user = refreshToken.getUser();

        if (!user.isEnabled()) {
            throw new Exception();
        }

        refreshToken.revoke();

        String newAccessToken =
                jwtService.generateAccessToken(user);

        String newRefreshTokenValue =
                refreshTokenGenerator.generate();

        RefreshToken newRefreshToken = new RefreshToken(
                newRefreshTokenValue,
                user,
                Instant.now().plus(
                        REFRESH_TOKEN_EXPIRATION_DAYS,
                        ChronoUnit.DAYS
                )
        );

        refreshTokenRepository.save(newRefreshToken);

        return new LoginResponse(
                newAccessToken,
                newRefreshTokenValue
        );
    }

    @Transactional
    public void logout(String token) {

        refreshTokenRepository
                .findByToken(token)
                .ifPresent(RefreshToken::revoke);
    }
}