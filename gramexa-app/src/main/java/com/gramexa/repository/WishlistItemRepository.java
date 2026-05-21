package com.gramexa.repository;

import com.gramexa.entity.Products;
import com.gramexa.entity.User;
import com.gramexa.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

  List<WishlistItem> findByUser(User user);

  Optional<WishlistItem> findByUserAndProduct(User user, Products product);

  long countByUser(User user);

  boolean existsByUserAndProduct(User user, Products product);

  void deleteByUserAndProduct(User user, Products product);
}
