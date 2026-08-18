package com.enzo.ecommerce.auth;

import com.enzo.ecommerce.auth.dto.*;
import com.enzo.ecommerce.auth.entity.RefreshToken;
import com.enzo.ecommerce.auth.repository.RefreshTokenRepository;
import com.enzo.ecommerce.shared.security.JwtService;
import com.enzo.ecommerce.user.UserService;
import com.enzo.ecommerce.user.dto.UserCreatedDto;
import com.enzo.ecommerce.user.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 30;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public AuthService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository,
            RefreshTokenGenerator refreshTokenGenerator
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest dto) throws Exception {
        UserCreatedDto userCreated = userService.createCustomer(
                dto.email(),
                dto.password(),
                dto.username()
        );

        return new RegisterResponse(
                userCreated.id(),
                userCreated.displayName(),
                userCreated.email()
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest dto) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshTokenValue = refreshTokenGenerator.generate();

        RefreshToken refreshToken = new RefreshToken(
                refreshTokenValue,
                user,
                Instant.now().plus(REFRESH_TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS)
        );

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(accessToken, refreshTokenValue);
    }

    @Transactional
    public LoginResponse refresh(RefreshRequest dto) throws Exception {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.refreshToken())
                .orElseThrow(Exception::new);

        if (refreshToken.isExpired() || refreshToken.isRevoked()) {
            throw new Exception();
        }

        User user = refreshToken.getUser();

        if (!user.isEnabled()) {
            throw new Exception();
        }

        refreshToken.revoke();

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshTokenValue = refreshTokenGenerator.generate();

        RefreshToken newRefreshToken = new RefreshToken(
                newRefreshTokenValue,
                user,
                Instant.now().plus(REFRESH_TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS)
        );

        refreshTokenRepository.save(newRefreshToken);

        return new LoginResponse(newAccessToken, newRefreshTokenValue);
    }

    @Transactional
    public void logout(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(RefreshToken::revoke);
    }
}