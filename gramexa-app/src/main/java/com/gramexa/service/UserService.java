package com.gramexa.service;

import com.gramexa.config.JwtUtil;
import com.gramexa.model.ChangePasswordRequest;
import com.gramexa.model.LoginResponse;
import com.gramexa.model.RegisterRequest;
import com.gramexa.entity.User;
import com.gramexa.exception.CustomException;
import com.gramexa.model.UpdateProfileRequest;
import com.gramexa.model.UserProfileResponse;
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

  @Autowired
  private JwtUtil jwtUtil;

  public String register(RegisterRequest request) {

    // Check email duplicate
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException("Email already registered");
    }

    // Check mobile duplicate
    if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
      throw new CustomException("Mobile number already registered");
    }

    // Map DTO → Entity
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setMobileNumber(request.getMobileNumber());

    // Encrypt password
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    user.setRole("USER");
    user.setCreatedAt(LocalDateTime.now());

    // Save to DB
    userRepository.save(user);

    return "User registered successfully";
  }


  public LoginResponse login(String username, String password) {

    User user = userRepository
            .findByEmailOrMobileNumber(username, username)
            .orElseThrow(() ->
                    new CustomException("User not found"));

    // Password check
    if (!passwordEncoder.matches(
            password,
            user.getPassword()
    )) {

      throw new CustomException("Invalid password");
    }

    // ADMIN APPROVAL CHECK
    if ("ADMIN".equals(user.getRole())
            && !user.isApproved()) {

      throw new CustomException(
              "Admin approval pending"
      );
    }

    // Generate JWT
    String token = jwtUtil.generateToken(user);

    return new LoginResponse(
            token,
            user.getName(),
            user.getMobileNumber(),
            user.getRole()
    );
  }

  public UserProfileResponse getProfile(String email) {
    User user = getUserByEmail(email);

    return mapProfile(user);
  }

  public UserProfileResponse updateProfile(String email, UpdateProfileRequest request) {
    User user = getUserByEmail(email);

    userRepository.findByEmail(request.getEmail())
            .filter(existingUser -> !existingUser.getId().equals(user.getId()))
            .ifPresent(existingUser -> {
              throw new CustomException("Email already registered");
            });

    userRepository.findByMobileNumber(request.getMobileNumber())
            .filter(existingUser -> !existingUser.getId().equals(user.getId()))
            .ifPresent(existingUser -> {
              throw new CustomException("Mobile number already registered");
            });

    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setMobileNumber(request.getMobileNumber());

    return mapProfile(userRepository.save(user));
  }

  public String changePassword(String email, ChangePasswordRequest request) {
    User user = getUserByEmail(email);

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new CustomException("Current password is incorrect");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    return "Password changed successfully";
  }

  private User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException("User not found"));
  }

  private UserProfileResponse mapProfile(User user) {
    return new UserProfileResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getMobileNumber(),
            user.getRole()
    );
  }
}
