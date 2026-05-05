package com.gramexa.controller;

import com.gramexa.model.LoginRequest;
import com.gramexa.model.LoginResponse;
import com.gramexa.model.RegisterRequest;
import com.gramexa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}