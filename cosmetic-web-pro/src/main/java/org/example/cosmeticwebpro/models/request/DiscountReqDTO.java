package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.ProductDiscount;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountReqDTO {
  private Integer discountPercent;
  private LocalDateTime fromDate;
  private LocalDateTime toDate;
  private String applyTo;
  private Integer minAmount;
  private Integer maxUsage;
}
