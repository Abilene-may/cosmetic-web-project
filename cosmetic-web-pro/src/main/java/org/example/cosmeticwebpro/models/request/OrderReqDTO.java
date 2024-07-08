package org.example.cosmeticwebpro.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.domains.Discount;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReqDTO {
  private Long userId;
  private Address address;
  private String note;
  private String paymentMethod;
  private Discount discount;
}
