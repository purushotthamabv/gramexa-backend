package com.gramexa.service;

import com.gramexa.entity.CartItem;
import com.gramexa.entity.Products;
import com.gramexa.entity.User;
import com.gramexa.exception.CustomException;
import com.gramexa.repository.CartItemRepository;
import com.gramexa.repository.ProductsRepository;
import com.gramexa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

  private final CartItemRepository cartItemRepository;
  private final ProductsRepository productsRepository;
  private final UserRepository userRepository;

  public CartService(
          CartItemRepository cartItemRepository,
          ProductsRepository productsRepository,
          UserRepository userRepository
  ) {
    this.cartItemRepository = cartItemRepository;
    this.productsRepository = productsRepository;
    this.userRepository = userRepository;
  }

  public List<CartItem> getItems(String email) {
    return cartItemRepository.findByUser(getUser(email));
  }

  @Transactional
  public CartItem addItem(String email, Long productId, Integer quantity) {
    User user = getUser(email);
    Products product = getProduct(productId);
    int requestedQuantity = quantity == null || quantity < 1 ? 1 : quantity;

    CartItem cartItem = cartItemRepository
            .findByUserAndProduct(user, product)
            .orElseGet(() -> {
              CartItem item = new CartItem();
              item.setUser(user);
              item.setProduct(product);
              item.setQuantity(0);
              return item;
            });

    cartItem.setQuantity(cartItem.getQuantity() + requestedQuantity);

    return cartItemRepository.save(cartItem);
  }

  @Transactional
  public void removeItem(String email, Long productId) {
    cartItemRepository.deleteByUserAndProduct(
            getUser(email),
            getProduct(productId)
    );
  }

  public long count(String email) {
    return cartItemRepository.countByUser(getUser(email));
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
