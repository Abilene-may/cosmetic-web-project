package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.DiscountReqDTO;
import org.example.cosmeticwebpro.models.request.DiscountUpdateReqDTO;
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
    var status = setDiscountStatus(discountReqDTO.getFromDate(), discountReqDTO.getToDate());
    LocalDateTime today = LocalDateTime.now();
    Discount discount =
        Discount.builder()
            .discountPercent(discountReqDTO.getDiscountPercent())
            .fromDate(discountReqDTO.getFromDate())
            .toDate(discountReqDTO.getToDate())
            .applyTo(discountReqDTO.getApplyTo())
            .minAmount(discountReqDTO.getMinAmount())
            .maxUsage(discountReqDTO.getMaxUsage())
            .discountStatus(status)
            .createdDate(today)
            .modifiedDate(today)
            .build();
    discountRepository.save(discount);
    // Associate productIdList with ProductDiscount entities
//    for (Long productId : discountReqDTO.getProductIdList()) {
//      ProductDiscount productDiscount = new ProductDiscount();
//      productDiscount.setDiscountId(discount.getId());
//      productDiscount.setProductId(productId);
//      // You may want to set other fields in ProductDiscount if needed
//      productDiscountRepository.save(productDiscount);
//    }
  }

  @Override
  public List<Discount> getAllDiscounts() throws CosmeticException {
    // Update the status of all discounts
    updateDiscountStatuses();

    return discountRepository.findAll();
  }

  @Transactional
  @Override
  public void updateADiscount(DiscountUpdateReqDTO discount)
      throws CosmeticException {
    var checkDiscount = this.getByDiscountId(discount.getId());
    LocalDateTime today = LocalDateTime.now();
    String status = setDiscountStatus(discount.getFromDate(), discount.getToDate());
    checkDiscount.setDiscountPercent(discount.getDiscountPercent());
    checkDiscount.setFromDate(discount.getFromDate());
    checkDiscount.setToDate(discount.getToDate());
    checkDiscount.setModifiedDate(today);
    checkDiscount.setApplyTo(discount.getApplyTo());
    checkDiscount.setMinAmount(discount.getMinAmount());
    checkDiscount.setMaxUsage(discount.getMaxUsage());
    checkDiscount.setDiscountStatus(status);
    discountRepository.save(checkDiscount);
  }

  private static String setDiscountStatus(LocalDateTime fromDate, LocalDateTime toDate) {
    LocalDateTime today = LocalDateTime.now();
    String status;
    if (fromDate.isAfter(today)) {
      status = Constants.UPCOMING;
    } else if (toDate.isBefore(today)) {
      status = Constants.EXPIRED;
    } else {
      status = Constants.ACTIVE;
    }
    return status;
  }

  @Override
  public Discount getByDiscountId(Long discountId) throws CosmeticException {
    var discount = discountRepository.findById(discountId);
    if (discount.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.DISCOUNT_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.DISCOUNT_NOT_FOUND));
    }
    return discount.get();
  }

  @Transactional
  @Override
  public List<Discount> findAllDiscountForProduct() throws CosmeticException{
    // Update the status of all discounts
    updateDiscountStatuses();

    // Fetch all discounts
    return discountRepository.findAllDiscountsFroProduct(Constants.ORDER);
  }

  // delete discount
  @Override
  @Transactional
  public void deleteDiscountById(Long discountId) throws CosmeticException {
    discountRepository.deleteProductDiscountByDiscountId(discountId);
    discountRepository.deleteById(discountId);
  }

  private void updateDiscountStatuses() {
    discountRepository.updateExpiredDiscounts(Constants.EXPIRED);
    discountRepository.updateUpcomingDiscounts(Constants.UPCOMING);
    discountRepository.updateActiveDiscounts(Constants.ACTIVE);
  }
}
