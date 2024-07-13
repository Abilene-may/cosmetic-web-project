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
  private List<DisplayProductDTO> displayProductDTOS;
  private List<Category> categoryList;

  private List<Brand> brandList;

}
