package com.example.wellness_javaproj.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private String SECRET_KEY = "wellness_secret_key_2025_alternative_therapies";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours [cite: 24]
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}