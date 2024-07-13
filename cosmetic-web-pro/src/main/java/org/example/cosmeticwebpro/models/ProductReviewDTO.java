package org.example.cosmeticwebpro.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {
  private Long id;
  private String content;
  private Integer productRating;
  private LocalDateTime createdDate;
  private Long ProductId;
  private Long orderId;
  private double averageRating;
  private int totalRating;
}
