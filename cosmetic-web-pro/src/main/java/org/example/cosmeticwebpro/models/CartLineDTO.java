package org.example.cosmeticwebpro.models;

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
public class CartLineDTO {
  private String imageUrl;

  private String title;

  private double price;

  private Integer quantity;
}
