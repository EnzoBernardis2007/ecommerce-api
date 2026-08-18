package com.enzo.ecommerce.user;

import com.enzo.ecommerce.user.dto.MeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<MeResponse> getMe(
            Authentication authentication
    ) throws Exception {

        String email = authentication.getName();

        return ResponseEntity.ok(
                service.findMe(email)
        );
    }
}