package com.gramexa.controller;

import com.gramexa.model.ChangePasswordRequest;
import com.gramexa.model.LoginRequest;
import com.gramexa.model.LoginResponse;
import com.gramexa.model.RegisterRequest;
import com.gramexa.model.UpdateProfileRequest;
import com.gramexa.model.UserProfileResponse;
import com.gramexa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

    String response = userService.register(request);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

    return ResponseEntity.ok(
            userService.login(request.getUsername(), request.getPassword())
    );
  }

  @GetMapping("/profile")
  public ResponseEntity<UserProfileResponse> profile(Authentication authentication) {

    return ResponseEntity.ok(
            userService.getProfile(authentication.getName())
    );
  }

  @PutMapping("/profile")
  public ResponseEntity<UserProfileResponse> updateProfile(
          Authentication authentication,
          @Valid @RequestBody UpdateProfileRequest request
  ) {

    return ResponseEntity.ok(
            userService.updateProfile(authentication.getName(), request)
    );
  }

  @PutMapping("/change-password")
  public ResponseEntity<String> changePassword(
          Authentication authentication,
          @Valid @RequestBody ChangePasswordRequest request
  ) {

    return ResponseEntity.ok(
            userService.changePassword(authentication.getName(), request)
    );
  }
}
