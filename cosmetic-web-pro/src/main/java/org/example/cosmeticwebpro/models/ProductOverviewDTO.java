package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.ProductImage;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverviewDTO {
  private Product product;

  private List<ProductImage> productImages;
}
