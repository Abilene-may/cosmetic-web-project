package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.ProductImage;
import org.example.cosmeticwebpro.domains.ProductReview;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDisplayDTO {
  private Product product;

  private List<ProductImage> productImages;

  private List<ProductReview> productReviews;
}
