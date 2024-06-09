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
public class HomeDisplayDTO {
  // list product is on sale
  private List<ProductOverviewDTO> listProductOnSale;

  private List<ProductOverviewDTO> listProductBestSeller;

  private List<ProductOverviewDTO> listProductTheMostView;

}
