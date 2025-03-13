package com.project.foodDiary.model.jwt;

import com.project.foodDiary.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final SecretKey signingKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());

    public String generateToken(long userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
                .signWith(signingKey)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            if (claims.getExpiration().before(new Date())) {
                System.out.println("Токен просрочен!");
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Ошибка валидации токена: " + e.getMessage());
            return false;
        }
    }



    public String extractUserId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Токен невалидный!", e);
        }
    }
}
