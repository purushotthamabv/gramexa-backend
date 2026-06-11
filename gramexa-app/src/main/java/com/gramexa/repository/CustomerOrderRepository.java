package com.gramexa.repository;

import com.gramexa.entity.CustomerOrder;
import com.gramexa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

  List<CustomerOrder> findByUserOrderByCreatedAtDesc(User user);
}
