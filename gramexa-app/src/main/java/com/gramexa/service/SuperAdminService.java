package com.gramexa.service;

import com.gramexa.entity.User;
import com.gramexa.exception.CustomException;
import com.gramexa.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperAdminService {

  @Autowired
  private UserRepository userRepository;

  // GET PENDING ADMINS
  public List<User> getPendingAdmins() {

    return userRepository.findByRoleAndApproved(
            "ADMIN",
            false
    );
  }

  // APPROVE ADMIN
  public String approveAdmin(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new CustomException(
                            "User not found"
                    )
            );

    // CHECK ROLE
    if (!"ADMIN".equals(user.getRole())) {

      throw new CustomException(
              "User is not an admin"
      );
    }

    // ALREADY APPROVED CHECK
    if (user.isApproved()) {

      throw new CustomException(
              "Admin already approved"
      );
    }

    // APPROVE
    user.setApproved(true);

    userRepository.save(user);

    return "Admin approved successfully";
  }
}