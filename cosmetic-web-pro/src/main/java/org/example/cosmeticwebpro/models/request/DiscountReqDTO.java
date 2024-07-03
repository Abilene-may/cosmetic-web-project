package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountReqDTO {
  private Integer discountPercent;
  private Date fromDate;
  private Date toDate;
  private Integer startHour;
  private Integer startMinute;
  private Integer endHour;
  private Integer endMinute;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private String applyTo;
  private Integer minAmount;
  private Integer maxUsage;
  private Integer totalUsage;
}
