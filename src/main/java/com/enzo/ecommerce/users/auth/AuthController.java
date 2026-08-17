package com.enzo.ecommerce.users.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest dto) throws Exception {
        RegisterResponse response = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest dto) throws Exception {
        return service.login(dto);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshRequest dto) throws Exception {
        return service.refresh(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest dto) throws Exception {
        service.logout(dto.refreshToken());

        return ResponseEntity.noContent().build();
    }

}
