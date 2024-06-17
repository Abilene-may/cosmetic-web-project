package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.OrderDetailDTO;

public interface OrderService {
  List<Order> getAllOrderForAUser(Long userId) throws CosmeticException;

  OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException;

  void updateStatusOfAnOrder(Long orderId) throws CosmeticException;
}
