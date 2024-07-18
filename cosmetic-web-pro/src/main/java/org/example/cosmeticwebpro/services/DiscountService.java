package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.DiscountReqDTO;
import org.example.cosmeticwebpro.models.request.DiscountUpdateReqDTO;

public interface DiscountService {
  void createADiscount(DiscountReqDTO discountReqDTO) throws CosmeticException;

  List<Discount> getAllDiscounts() throws CosmeticException;

  void updateADiscount(DiscountUpdateReqDTO discount) throws CosmeticException;

  Discount getByDiscountId(Long discountId) throws CosmeticException;

  List<Discount> findAllDiscountForProduct() throws CosmeticException;

  void deleteDiscountById(Long discountId) throws CosmeticException;
}
