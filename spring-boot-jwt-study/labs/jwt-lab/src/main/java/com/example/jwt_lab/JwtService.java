package com.example.jwt_lab;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component 
public class JwtService {
    
    private static final String SECRET = "jwt-lab-demo-secret-key-change-me-0123456789";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final long TTL = 5 * 60 * 1000;

    public String create(String username){
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TTL))
                .signWith(KEY)
                .compact();
    }

    public String verify(String token){
        try{
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch(JwtException e){
            return null;
        }
    }

}
