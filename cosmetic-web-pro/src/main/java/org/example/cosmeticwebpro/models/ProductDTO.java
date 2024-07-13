package org.example.cosmeticwebpro.models;

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
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  private Long id;
  private String title;
  private String description;
  private double currentCost;
  private String madeIn;
  private Integer capacity;
  private Integer quantity;
  private String productStatus;
  private Integer countView;
  private Integer countPurchase;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long brandId;
  private Long categoryId;
  private Discount productDiscount;
  private String categoryName;
  private String brandName;
}
