package com.gramexa.service;

import com.gramexa.dto.RegisterRequest;
import com.gramexa.entity.User;
import com.gramexa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String register(RegisterRequest request) {

    // Check email duplicate
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already registered");
    }

    // Check mobile duplicate
    if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
      throw new RuntimeException("Mobile number already registered");
    }

    // Map DTO → Entity
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setMobileNumber(request.getMobileNumber());

    // 🔐 Encrypt password
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    user.setRole("USER");
    user.setCreatedAt(LocalDateTime.now());

    // 💾 Save to DB
    userRepository.save(user);

    return "User registered successfully";
  }
}