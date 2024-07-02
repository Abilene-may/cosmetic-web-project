package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.domains.Discount;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReviewDTO {
  private List<Address> addressList;

  private Discount discount;
}
