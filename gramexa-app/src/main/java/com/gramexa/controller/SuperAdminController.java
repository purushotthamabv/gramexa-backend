package com.gramexa.controller;

import com.gramexa.entity.User;
import com.gramexa.service.SuperAdminService;

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
  public List<User> getPendingAdmins() {

    return superAdminService.getPendingAdmins();
  }

  // APPROVE ADMIN
  @PutMapping("/approve-admin/{id}")
  public String approveAdmin(
          @PathVariable Long id
  ) {

    return superAdminService.approveAdmin(id);
  }
}