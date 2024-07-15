package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;
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
  private Long id;
  private Integer discountPercent;
  private LocalDateTime fromDate;
  private LocalDateTime toDate;
  private String applyTo;
  private Integer minAmount;
  private Integer maxUsage;
}
