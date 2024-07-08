package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.models.OrderReviewDTO;
import org.example.cosmeticwebpro.models.request.OrderReqDTO;

public interface OrderService {
  List<Order> getAllOrderForAUser(Long userId) throws CosmeticException;

  OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException;

  void updateStatusOfAnOrder(Long orderId) throws CosmeticException;

  OrderDetailDTO createAnOrder(OrderReqDTO orderReqDTO) throws CosmeticException;

  Order cancelAnOrder(Long orderId) throws CosmeticException;
}
