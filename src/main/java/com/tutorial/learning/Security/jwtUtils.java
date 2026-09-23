package com.tutorial.learning.Security;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.tutorial.learning.Entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class jwtUtils {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
    private static final long EXPIRATION_TIME = 36000000;

    public static String generateToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole().toString())
                .issuer("Nienvv")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Long extractUser(String token) {
        Claims claims = parser(token);
        return claims.get("id", Long.class);
    }

    public static boolean validateToken(String token) {
        Claims claims = parser(token);
        return claims.getExpiration().after(new Date());
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Long userId = extractUser(token);
        String role = parser(token).get("role", String.class);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

    public static Claims parser(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }
}
