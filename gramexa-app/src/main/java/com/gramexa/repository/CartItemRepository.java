package com.gramexa.repository;

import com.gramexa.entity.CartItem;
import com.gramexa.entity.Products;
import com.gramexa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  List<CartItem> findByUser(User user);

  Optional<CartItem> findByUserAndProduct(User user, Products product);

  long countByUser(User user);

  void deleteByUserAndProduct(User user, Products product);

  void deleteByUser(User user);
}
