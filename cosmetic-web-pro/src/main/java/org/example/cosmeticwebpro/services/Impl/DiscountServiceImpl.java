package org.example.cosmeticwebpro.services.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.DiscountReqDTO;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.services.DiscountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
  private final DiscountRepository discountRepository;

  // create a new discount
  @Override
  public void createADiscount(DiscountReqDTO discountReqDTO) throws CosmeticException {
    Discount discount =
        Discount.builder()
            .discountPercent(discountReqDTO.getDiscountPercent())
            .fromDate(discountReqDTO.getFromDate())
            .toDate(discountReqDTO.getToDate())
            .startHour(discountReqDTO.getStartHour())
            .startMinute(discountReqDTO.getStartMinute())
            .endHour(discountReqDTO.getEndHour())
            .endMinute(discountReqDTO.getEndMinute())
            .createdDate(discountReqDTO.getCreatedDate())
            .modifiedDate(discountReqDTO.getModifiedDate())
            .applyTo(discountReqDTO.getApplyTo())
            .minAmount(discountReqDTO.getMinAmount())
            .maxUsage(discountReqDTO.getMaxUsage())
            .totalUsage(discountReqDTO.getTotalUsage()).build();
    discountRepository.save(discount);
  }

  @Override
  public List<Discount> getAllDiscounts() throws CosmeticException {
    return discountRepository.findAll();
  }
}
