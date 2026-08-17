package com.enzo.ecommerce.security;

import com.enzo.ecommerce.users.entities.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateAccessToken(User user) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim(
                        "roles",
                        user.getAuthorities()
                                .stream()
                                .map(Object::toString)
                                .toList()
                )
                .issuedAt(Date.from(now))
                .expiration(
                        Date.from(now.plusSeconds(900))
                )
                .signWith(secretKey)
                .compact();
    }
}