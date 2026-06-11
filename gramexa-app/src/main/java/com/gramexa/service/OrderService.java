package com.gramexa.service;

import com.gramexa.entity.CartItem;
import com.gramexa.entity.CustomerOrder;
import com.gramexa.entity.OrderItem;
import com.gramexa.entity.Products;
import com.gramexa.entity.User;
import com.gramexa.exception.CustomException;
import com.gramexa.model.CheckoutRequest;
import com.gramexa.model.OrderItemResponse;
import com.gramexa.model.OrderResponse;
import com.gramexa.repository.CartItemRepository;
import com.gramexa.repository.CustomerOrderRepository;
import com.gramexa.repository.ProductsRepository;
import com.gramexa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

  private final CustomerOrderRepository orderRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductsRepository productsRepository;
  private final UserRepository userRepository;

  public OrderService(CustomerOrderRepository orderRepository, CartItemRepository cartItemRepository, ProductsRepository productsRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.cartItemRepository = cartItemRepository;
    this.productsRepository = productsRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public OrderResponse checkout(String email, CheckoutRequest request) {
    User user = getUser(email);
    List<CartItem> cartItems = cartItemRepository.findByUser(user);

    if (cartItems.isEmpty()) {
      throw new CustomException("Cart is empty");
    }

    CustomerOrder order = new CustomerOrder();
    order.setUser(user);
    order.setDeliveryName(request.getDeliveryName());
    order.setDeliveryMobile(request.getDeliveryMobile());
    order.setDeliveryAddress(request.getDeliveryAddress());
    order.setDeliveryPincode(request.getDeliveryPincode());
    order.setPaymentMode("CASH_ON_DELIVERY");
    order.setStatus("PLACED");

    double totalAmount = 0;

    for (CartItem cartItem : cartItems) {
      Products product = cartItem.getProduct();
      int quantity = cartItem.getQuantity() == null || cartItem.getQuantity() < 1 ? 1 : cartItem.getQuantity();

      validateProductForCheckout(product, quantity);

      double unitPrice = getSellingPrice(product);
      double lineTotal = unitPrice * quantity;

      OrderItem orderItem = new OrderItem();
      orderItem.setProduct(product);
      orderItem.setProductName(product.getProductName());
      orderItem.setUnitPrice(unitPrice);
      orderItem.setQuantity(quantity);
      orderItem.setLineTotal(lineTotal);
      order.addItem(orderItem);

      product.setStockQuantity(product.getStockQuantity() - quantity);
      productsRepository.save(product);
      totalAmount += lineTotal;
    }

    order.setTotalAmount(totalAmount);
    CustomerOrder savedOrder = orderRepository.save(order);
    cartItemRepository.deleteByUser(user);

    return mapOrder(savedOrder);
  }

  public List<OrderResponse> getOrders(String email) {
    return orderRepository.findByUserOrderByCreatedAtDesc(getUser(email))
            .stream()
            .map(this::mapOrder)
            .toList();
  }

  private void validateProductForCheckout(Products product, int quantity) {
    if (!"ACTIVE".equals(product.getProductStatus())) {
      throw new CustomException(product.getProductName() + " is not available");
    }

    if (Boolean.FALSE.equals(product.getDeliveryAvailable())) {
      throw new CustomException(product.getProductName() + " is not available for delivery");
    }

    if (product.getStockQuantity() == null || product.getStockQuantity() < quantity) {
      throw new CustomException(product.getProductName() + " has insufficient stock");
    }
  }

  private double getSellingPrice(Products product) {
    if (product.getOfferPrice() != null && product.getOfferPrice() > 0) {
      return product.getOfferPrice();
    }

    return product.getProductPrice() == null ? 0 : product.getProductPrice();
  }

  private User getUser(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException("User not found"));
  }

  private OrderResponse mapOrder(CustomerOrder order) {
    return new OrderResponse(
            order.getId(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getPaymentMode(),
            order.getDeliveryName(),
            order.getDeliveryMobile(),
            order.getDeliveryAddress(),
            order.getDeliveryPincode(),
            order.getCreatedAt(),
            order.getItems().stream()
                    .map(item -> new OrderItemResponse(
                            item.getId(),
                            item.getProduct().getId(),
                            item.getProductName(),
                            item.getUnitPrice(),
                            item.getQuantity(),
                            item.getLineTotal()
                    ))
                    .toList()
    );
  }
}
