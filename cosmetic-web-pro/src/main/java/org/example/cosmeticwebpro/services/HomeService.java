package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.HomeDisplayDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;

public interface HomeService {
  HomeDisplayDTO displayHomeScreen() throws CosmeticException;

  ProductDisplayDTO viewAProductDetail(Long productId) throws CosmeticException;

  List<ProductOverviewDTO> filterProducts(String titleProduct, String categoryName, String sortCode)
      throws CosmeticException;
}
