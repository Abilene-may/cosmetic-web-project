package org.example.cosmeticwebpro.models.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Discount;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountUpdateReqDTO {
  private Discount discount;
  private List<Long> productIdList;
}
