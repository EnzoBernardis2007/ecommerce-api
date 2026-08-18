package com.enzo.ecommerce.users.me;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    private final MeService service;

    public MeController(MeService service) {
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