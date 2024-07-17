package org.example.cosmeticwebpro.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayStatisticalDTO;
import org.example.cosmeticwebpro.repositories.BrandRepository;
import org.example.cosmeticwebpro.repositories.OrderRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.StatisticalService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final BrandRepository brandRepository;

  @Override
  public DisplayStatisticalDTO displayStatistical(Integer year) throws CosmeticException {
    var statisticalDTO = orderRepository.statisticalOrder(year);
    var allRevenueOfBrandsByYear = brandRepository.findAllRevenueOfBrandsByYear(year);
    var productStatisticDTOS = productRepository.findAllProductsOutOfStock();
    var userPotentialDTOS = orderRepository.findALlUserPotential();

    DisplayStatisticalDTO displayStatisticalDTO =
        DisplayStatisticalDTO.builder()
            .statisticalDTO(statisticalDTO.get())
            .revenueOfBrandDTO(allRevenueOfBrandsByYear)
            .productStatisticDTOS(productStatisticDTOS)
            .userPotentialDTOS(userPotentialDTOS)
            .build();
    return displayStatisticalDTO;
  }
}
