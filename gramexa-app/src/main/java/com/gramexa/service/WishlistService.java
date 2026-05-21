package com.gramexa.service;

import com.gramexa.entity.Products;
import com.gramexa.entity.User;
import com.gramexa.entity.WishlistItem;
import com.gramexa.exception.CustomException;
import com.gramexa.repository.ProductsRepository;
import com.gramexa.repository.UserRepository;
import com.gramexa.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {

  private final WishlistItemRepository wishlistItemRepository;
  private final ProductsRepository productsRepository;
  private final UserRepository userRepository;

  public WishlistService(
          WishlistItemRepository wishlistItemRepository,
          ProductsRepository productsRepository,
          UserRepository userRepository
  ) {
    this.wishlistItemRepository = wishlistItemRepository;
    this.productsRepository = productsRepository;
    this.userRepository = userRepository;
  }

  public List<WishlistItem> getItems(String email) {
    return wishlistItemRepository.findByUser(getUser(email));
  }

  @Transactional
  public WishlistItem addItem(String email, Long productId) {
    User user = getUser(email);
    Products product = getProduct(productId);

    return wishlistItemRepository
            .findByUserAndProduct(user, product)
            .orElseGet(() -> {
              WishlistItem item = new WishlistItem();
              item.setUser(user);
              item.setProduct(product);
              return wishlistItemRepository.save(item);
            });
  }

  @Transactional
  public boolean toggleItem(String email, Long productId) {
    User user = getUser(email);
    Products product = getProduct(productId);

    if (wishlistItemRepository.existsByUserAndProduct(user, product)) {
      wishlistItemRepository.deleteByUserAndProduct(user, product);
      return false;
    }

    WishlistItem item = new WishlistItem();
    item.setUser(user);
    item.setProduct(product);
    wishlistItemRepository.save(item);

    return true;
  }

  @Transactional
  public void removeItem(String email, Long productId) {
    wishlistItemRepository.deleteByUserAndProduct(
            getUser(email),
            getProduct(productId)
    );
  }

  public long count(String email) {
    return wishlistItemRepository.countByUser(getUser(email));
  }

  private User getUser(String email) {
    return userRepository
            .findByEmail(email)
            .orElseThrow(() -> new CustomException("User not found"));
  }

  private Products getProduct(Long productId) {
    return productsRepository
            .findById(productId)
            .orElseThrow(() -> new CustomException("Product not found"));
  }
}
