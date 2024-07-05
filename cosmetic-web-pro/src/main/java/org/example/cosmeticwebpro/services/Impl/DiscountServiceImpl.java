package org.example.cosmeticwebpro.services.Impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.ProductDiscount;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.DiscountReqDTO;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.repositories.ProductDiscountRepository;
import org.example.cosmeticwebpro.services.DiscountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
  private final DiscountRepository discountRepository;
  private final ProductDiscountRepository productDiscountRepository;

  // create a new discount
  @Override
  public void createADiscount(DiscountReqDTO discountReqDTO) throws CosmeticException {
    LocalDateTime today = LocalDateTime.now();
    String status;
    if (discountReqDTO.getFromDate().isAfter(today)) {
      status = Constants.UPCOMING;
    } else if (discountReqDTO.getToDate().isBefore(today)) {
      status = Constants.EXPIRED;
    } else {
      status = Constants.ACTIVE;
    }
    Discount discount =
        Discount.builder()
            .discountPercent(discountReqDTO.getDiscountPercent())
            .fromDate(discountReqDTO.getFromDate())
            .toDate(discountReqDTO.getToDate())
            .createdDate(discountReqDTO.getCreatedDate())
            .modifiedDate(discountReqDTO.getModifiedDate())
            .applyTo(discountReqDTO.getApplyTo())
            .minAmount(discountReqDTO.getMinAmount())
            .maxUsage(discountReqDTO.getMaxUsage())
            .totalUsage(discountReqDTO.getTotalUsage())
            .discountStatus(status)
            .build();
    discountRepository.save(discount);
    // Associate productIdList with ProductDiscount entities
    for (Long productId : discountReqDTO.getProductIdList()) {
      ProductDiscount productDiscount = new ProductDiscount();
      productDiscount.setDiscount(discount);
      productDiscount.setProductId(productId);
      // You may want to set other fields in ProductDiscount if needed
      productDiscountRepository.save(productDiscount);
    }
  }

  @Override
  public List<Discount> getAllDiscounts() throws CosmeticException {
    return discountRepository.findAll();
  }
}
