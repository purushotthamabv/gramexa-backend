package com.gramexa.config;

import com.gramexa.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final String SECRET = "gramexa-secret-key-gramexa-secret-key";
  private final long EXPIRATION = 1000 * 60 * 60;

  public Key getKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(User user) {

    return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("name", user.getName())
            .claim("mobile", user.getMobileNumber())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}
