package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.domains.OrderDetail;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
  private Order order;
  private List<OrderDetail> orderDetail;
  private Discount discountOrder;
  private double totalAmount;
}
