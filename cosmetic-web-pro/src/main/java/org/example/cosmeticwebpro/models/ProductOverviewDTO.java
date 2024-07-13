package org.example.cosmeticwebpro.models;

import java.util.List;
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
public class ProductOverviewDTO {
  private ProductDTO productDTO;
  private String categoryName;
  private String brandName;
  private List<String> imageUrls;
}
