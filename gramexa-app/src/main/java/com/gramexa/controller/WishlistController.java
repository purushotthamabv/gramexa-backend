package com.gramexa.controller;

import com.gramexa.entity.WishlistItem;
import com.gramexa.model.CountResponse;
import com.gramexa.model.ProductActionRequest;
import com.gramexa.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @GetMapping
  public List<WishlistItem> getWishlist(Authentication authentication) {
    return wishlistService.getItems(authentication.getName());
  }

  @PostMapping("/add")
  public WishlistItem addToWishlist(
          Authentication authentication,
          @RequestBody ProductActionRequest request
  ) {
    return wishlistService.addItem(
            authentication.getName(),
            request.getProductId()
    );
  }

  @PostMapping("/toggle")
  public boolean toggleWishlist(
          Authentication authentication,
          @RequestBody ProductActionRequest request
  ) {
    return wishlistService.toggleItem(
            authentication.getName(),
            request.getProductId()
    );
  }

  @DeleteMapping("/remove/{productId}")
  public void removeFromWishlist(
          Authentication authentication,
          @PathVariable Long productId
  ) {
    wishlistService.removeItem(authentication.getName(), productId);
  }

  @GetMapping("/count")
  public CountResponse count(Authentication authentication) {
    return new CountResponse(
            wishlistService.count(authentication.getName())
    );
  }
}
