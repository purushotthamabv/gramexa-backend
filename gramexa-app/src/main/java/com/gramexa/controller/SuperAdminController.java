package com.gramexa.controller;

import com.gramexa.entity.User;
import com.gramexa.service.SuperAdminService;
import com.gramexa.model.AdminUserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
public class SuperAdminController {

  @Autowired
  private SuperAdminService superAdminService;

  // GET PENDING ADMINS
  @GetMapping("/pending-admins")
  public List<AdminUserResponse> getPendingAdmins() {
    return superAdminService.getPendingAdmins().stream()
            .map(u -> new AdminUserResponse(u.getId(), u.getName(), u.getEmail(), u.getMobileNumber(), u.getRole(), u.isApproved(), u.getCreatedAt()))
            .toList();
  }

  // APPROVE ADMIN
  @PutMapping("/approve-admin/{id}")
  public String approveAdmin(
          @PathVariable Long id
  ) {

    return superAdminService.approveAdmin(id);
  }
}
