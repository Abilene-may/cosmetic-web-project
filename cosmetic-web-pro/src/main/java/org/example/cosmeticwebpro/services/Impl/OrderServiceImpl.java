package org.example.cosmeticwebpro.services.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.OrderDetailDTO;
import org.example.cosmeticwebpro.repositories.CategoryRepository;
import org.example.cosmeticwebpro.repositories.OrderDetailRepository;
import org.example.cosmeticwebpro.repositories.OrderRepository;
import org.example.cosmeticwebpro.services.OrderService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  @Override
  public List<Order> getAllInvoice() throws CosmeticException {
    return List.of();
  }

  @Override
  public OrderDetailDTO showDetailAnOrder(Long orderId) throws CosmeticException {
    return null;
  }

  // update status of an order
  @Override
  public void updateStatusOfAnOrder(Long orderId) throws CosmeticException {

  }
}
