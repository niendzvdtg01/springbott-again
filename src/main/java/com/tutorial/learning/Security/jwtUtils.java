package com.tutorial.learning.Security;

import java.util.Date;

import javax.crypto.SecretKey;

import com.tutorial.learning.Entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class jwtUtils {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
    private static final long EXPIRATION_TIME = 36000000;

    public static String generateToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issuer("Nienvv")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Integer extracUser(String token){
        Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        return claims.get("id", Integer.class);
    }

    public static boolean validatToken(String token){
        Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        return claims.getExpiration().after(new Date());
    }

    
}
