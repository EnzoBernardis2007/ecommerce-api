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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository,
            RefreshTokenGenerator refreshTokenGenerator
    ) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest dto) throws Exception {

        // treatment exception
        if(userRepository.existsByEmail(dto.email())) throw new Exception();

        Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow(Exception::new);

        String hashedPassword = encoder.encode(dto.password());

        User user = new User(dto.email(), hashedPassword, dto.username());

        user.addRole(customerRole);

        userRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest dto) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);

        String refreshTokenValue = refreshTokenGenerator.generate();

        RefreshToken refreshToken = new RefreshToken(refreshTokenValue, user, Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return new LoginResponse(
                accessToken,
                refreshTokenValue
        );
    }

    @Transactional
    public LoginResponse refresh(RefreshRequest dto) throws Exception {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.refreshToken()).orElseThrow(Exception::new);

        if(refreshToken.isExpired() || refreshToken.isRevoked()) throw new Exception();

        User user = refreshToken.getUser();

        if (!user.isEnabled()) throw new Exception();

        refreshToken.revoke();

        String newAcessToken = jwtService.generateAccessToken(user);

        String newRefreshTokenValue = refreshTokenGenerator.generate();

        RefreshToken newRefreshToken = new RefreshToken(newRefreshTokenValue, user, Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(newRefreshToken);

        return new LoginResponse(newAcessToken, newRefreshTokenValue);
    }

    @Transactional
    public void logout(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(RefreshToken::revoke);
    }
}
