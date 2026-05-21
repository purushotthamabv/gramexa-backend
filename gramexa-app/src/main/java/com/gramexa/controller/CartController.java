package com.gramexa.controller;

import com.gramexa.entity.CartItem;
import com.gramexa.model.CountResponse;
import com.gramexa.model.ProductActionRequest;
import com.gramexa.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping
  public List<CartItem> getCart(Authentication authentication) {
    return cartService.getItems(authentication.getName());
  }

  @PostMapping("/add")
  public CartItem addToCart(
          Authentication authentication,
          @RequestBody ProductActionRequest request
  ) {
    return cartService.addItem(
            authentication.getName(),
            request.getProductId(),
            request.getQuantity()
    );
  }

  @DeleteMapping("/remove/{productId}")
  public void removeFromCart(
          Authentication authentication,
          @PathVariable Long productId
  ) {
    cartService.removeItem(authentication.getName(), productId);
  }

  @GetMapping("/count")
  public CountResponse count(Authentication authentication) {
    return new CountResponse(
            cartService.count(authentication.getName())
    );
  }
}
