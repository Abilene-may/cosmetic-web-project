package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayOrderDTO;
import org.example.cosmeticwebpro.models.DisplayOrderDetailDTO;
import org.example.cosmeticwebpro.models.request.OrderReqDTO;

public interface OrderService {
  List<Order> getAllOrderForAUser(Long userId) throws CosmeticException;

  DisplayOrderDTO showDetailAnOrder(Long orderId) throws CosmeticException;

  DisplayOrderDetailDTO createAnOrder(OrderReqDTO orderReqDTO) throws CosmeticException;

  Order updateStatusAnOrderForUser(Long orderId, String status) throws CosmeticException;

  List<Order> searchOrderByStatusForAdmin(String orderStatus) throws CosmeticException;

  Order updateStatusAnOrderForAdmin(Long orderId, String status) throws CosmeticException;

  Order getByOrderId(Long orderId) throws CosmeticException;

  List<Order> getAllOrderForAdmin() throws CosmeticException;
}
