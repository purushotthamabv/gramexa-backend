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

    return userRepository.findByAdminRequestPendingTrue();
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
    if (!user.isAdminRequestPending()) {

      throw new CustomException(
              "No pending admin request found"
      );
    }

    // ALREADY APPROVED CHECK
    // Approve the pending request, even if approved was set inconsistently.
    user.setApproved(true);
    user.setRole("ADMIN");
    user.setAdminRequestPending(false);

    userRepository.save(user);

    return "Admin approved successfully";
  }
}
