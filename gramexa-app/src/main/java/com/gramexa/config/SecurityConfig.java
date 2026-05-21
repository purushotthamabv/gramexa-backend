package com.gramexa.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(8);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
            List.of(
                    "http://localhost:4200",
                    "http://127.0.0.1:4200"
            )
    );
    configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
    );
    configuration.setAllowedHeaders(
            List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With")
    );
    configuration.setExposedHeaders(
            List.of("Authorization")
    );
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
          HttpSecurity http
  ) throws Exception {

    http

            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()

                    // PUBLIC APIs
                    .requestMatchers("/api/auth/**")
                    .permitAll()

                    .requestMatchers("/api/products/all-products", "/api/products/{id}")
                    .permitAll()

                    // ONLY SUPER ADMIN
                    .requestMatchers("/api/super-admin/**")
                    .hasRole("SUPER_ADMIN")

                    // LOGIN REQUIRED
                    .anyRequest()
                    .authenticated()
            )

            // JWT FILTER
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
  }
}
