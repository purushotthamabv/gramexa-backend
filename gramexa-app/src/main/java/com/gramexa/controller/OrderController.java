package com.gramexa.controller;

import com.gramexa.model.CheckoutRequest;
import com.gramexa.model.OrderResponse;
import com.gramexa.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/checkout")
  public OrderResponse checkout(Authentication authentication, @Valid @RequestBody CheckoutRequest request) {
    return orderService.checkout(authentication.getName(), request);
  }

  @GetMapping
  public List<OrderResponse> getOrders(Authentication authentication) {
    return orderService.getOrders(authentication.getName());
  }
}
