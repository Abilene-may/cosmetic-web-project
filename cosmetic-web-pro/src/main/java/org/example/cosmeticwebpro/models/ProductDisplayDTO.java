package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.example.cosmeticwebpro.models.request.DisplayReviewDTO;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDisplayDTO {
  private DisplayProductDTO displayProductDTO;
  private List<String> productImages;
  private List<DisplayReviewDTO> productReviews;
}
