package org.example.cosmeticwebpro.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayStatisticalDTO {
  private StatisticalDTO statisticalDTO;
  private List<RevenueOfBrandDTO> revenueOfBrandDTO;
  private List<ProductStatisticDTO> productStatisticDTOS;
  private List<UserPotentialDTO> userPotentialDTOS;
}
