package com.gramexa.config;

import com.gramexa.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

import java.util.Date;

@Component
public class JwtUtil {

  private final String SECRET =
          "gramexa-secret-key-gramexa-secret-key";

  private final long EXPIRATION =
          1000 * 60 * 60;

  // SECRET KEY
  public Key getKey() {

    return Keys.hmacShaKeyFor(
            SECRET.getBytes()
    );
  }

  // GENERATE TOKEN
  public String generateToken(User user) {

    return Jwts.builder()

            .setSubject(user.getEmail())

            .claim("name", user.getName())

            .claim("mobile", user.getMobileNumber())

            .claim("role", user.getRole())

            .setIssuedAt(new Date())

            .setExpiration(
                    new Date(
                            System.currentTimeMillis()
                                    + EXPIRATION
                    )
            )

            .signWith(
                    getKey(),
                    SignatureAlgorithm.HS256
            )

            .compact();
  }

  // EXTRACT USERNAME
  public String extractUsername(String token) {

    return extractAllClaims(token)
            .getSubject();
  }

  // EXTRACT CLAIMS
  public Claims extractAllClaims(String token) {

    return Jwts.parserBuilder()

            .setSigningKey(getKey())

            .build()

            .parseClaimsJws(token)

            .getBody();
  }

  // CHECK TOKEN EXPIRY
  public boolean isTokenExpired(String token) {

    return extractAllClaims(token)
            .getExpiration()
            .before(new Date());
  }

  // VALIDATE TOKEN
  public boolean validateToken(
          String token,
          UserDetails userDetails
  ) {

    final String username =
            extractUsername(token);

    return username.equals(
            userDetails.getUsername()
    ) && !isTokenExpired(token);
  }
}