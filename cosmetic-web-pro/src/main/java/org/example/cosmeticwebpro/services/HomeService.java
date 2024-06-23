package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.HomeDisplayDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;

public interface HomeService {
  HomeDisplayDTO displayHomeScreen() throws CosmeticException;

  ProductDisplayDTO viewAProductDetail(Long productId) throws CosmeticException;
}
