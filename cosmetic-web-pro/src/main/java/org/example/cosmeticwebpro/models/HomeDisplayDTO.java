package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Brand;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.domains.User;
import org.mapstruct.control.MappingControl.Use;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeDisplayDTO {
  private Integer totalQuantityCart;

  // list product is on sale
  private List<ProductOverviewDTO> listProductOnSale;

  private List<ProductOverviewDTO> listProductBestSeller;

  private List<ProductOverviewDTO> listProductTheMostView;

  private User account;

  private List<Category> categoryList;

  private List<Brand> brandList;

}
