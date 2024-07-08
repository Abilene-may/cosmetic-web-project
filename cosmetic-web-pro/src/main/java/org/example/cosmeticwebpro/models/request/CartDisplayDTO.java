package org.example.cosmeticwebpro.models.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.models.ProductDTO;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDisplayDTO {
  private List<ProductDTO> productDTOS;

  private Discount discount;

  private Integer totalItems;

  private double totalCost;

  private double totalFinalPrice;
}
