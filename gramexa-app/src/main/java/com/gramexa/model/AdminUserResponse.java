package com.gramexa.model;

import java.time.LocalDateTime;

public record AdminUserResponse(Long id, String name, String email, String mobileNumber,
                                String role, boolean approved, LocalDateTime createdAt) {}
