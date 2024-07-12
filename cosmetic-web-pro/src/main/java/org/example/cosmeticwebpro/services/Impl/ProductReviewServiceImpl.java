package org.example.cosmeticwebpro.services.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.ProductReviewReqDTO;
import org.example.cosmeticwebpro.repositories.ProductReviewRepository;
import org.example.cosmeticwebpro.services.OrderService;
import org.example.cosmeticwebpro.services.ProductReviewService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
  private final ProductReviewRepository productReviewRepository;
  private final OrderService orderService;

  // create a new review
  @Override
  public ProductReview create(ProductReviewReqDTO reqDTO) throws CosmeticException {
    var order = orderService.getByOrderId(reqDTO.getOrderId());
    if (order.getStatus().equals(Constants.ORDER_RECEIVED)) {
      ProductReview productReview =
          ProductReview.builder()
              .ProductId(reqDTO.getProductId())
              .orderId(reqDTO.getOrderId())
              .content(reqDTO.getContent())
              .productRating(reqDTO.getProductRating())
              .build();
      productReviewRepository.save(productReview);
      return productReview;
    }
    throw new CosmeticException(
        ExceptionUtils.PRODUCT_REVIEW_ERROR_1,
        ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_ERROR_1));
  }


  // view a review by id
  @Override
  public ProductReview getById(Long productReviewId) throws CosmeticException {
    var productReview = productReviewRepository.findById(productReviewId);
    if (productReview.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_REVIEW_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_NOT_FOUND));
    }
    return productReview.get();
  }

  @Override
  public List<ProductReview> getAllByProductId(Long productId) throws CosmeticException {
    return productReviewRepository.findAllByProductId(productId);
  }
}
