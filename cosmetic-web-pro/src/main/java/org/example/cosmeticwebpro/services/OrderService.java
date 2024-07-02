package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.models.OrderReviewDTO;

public interface OrderService {
  List<Order> getAllOrderForAUser(Long userId) throws CosmeticException;

  OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException;

  void updateStatusOfAnOrder(Long orderId) throws CosmeticException;

  OrderDetailDTO createAnOrder(Long userId, Address address, Long discountId) throws CosmeticException;

  OrderReviewDTO orderReview(Long userId) throws CosmeticException;
}
