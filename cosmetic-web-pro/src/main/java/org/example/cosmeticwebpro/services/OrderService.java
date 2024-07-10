package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.models.request.OrderReqDTO;

public interface OrderService {
  List<Order> getAllOrderForAUser(Long userId) throws CosmeticException;

  OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException;

  OrderDetailDTO createAnOrder(OrderReqDTO orderReqDTO) throws CosmeticException;

  Order updateStatusAnOrderForUser(Long orderId, String status) throws CosmeticException;

  List<Order> searchOrderByStatusForAdmin(String orderStatus) throws CosmeticException;

  Order updateStatusAnOrderForAdmin(Long orderId, String status) throws CosmeticException;
}
