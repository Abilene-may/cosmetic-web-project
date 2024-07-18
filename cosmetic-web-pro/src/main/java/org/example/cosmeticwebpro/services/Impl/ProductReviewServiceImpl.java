package org.example.cosmeticwebpro.services.Impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.DisplayReviewDTO;
import org.example.cosmeticwebpro.models.request.ProductReviewReqDTO;
import org.example.cosmeticwebpro.repositories.OrderDetailRepository;
import org.example.cosmeticwebpro.repositories.ProductReviewRepository;
import org.example.cosmeticwebpro.services.OrderService;
import org.example.cosmeticwebpro.services.ProductReviewService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductReviewServiceImpl implements ProductReviewService {
  private final ProductReviewRepository productReviewRepository;
  private final OrderService orderService;
  private final OrderDetailRepository orderDetailRepository;

  public ProductReviewServiceImpl(
      @Lazy ProductReviewRepository productReviewRepository,
      @Lazy OrderService orderService,
      @Lazy OrderDetailRepository orderDetailRepository) {
    super();
    this.productReviewRepository = productReviewRepository;
    this.orderService = orderService;
    this.orderDetailRepository = orderDetailRepository;
  }

  // create a new review
  @Override
  public ProductReview create(ProductReviewReqDTO reqDTO) throws CosmeticException {
    if (reqDTO.getProductId() == null || reqDTO.getOrderId() == null) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_REVIEW_ERROR_2,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_ERROR_2));
    }

    var order = orderService.getByOrderId(reqDTO.getOrderId());
    if (!order.getStatus().equals(Constants.DELIVERY_SUCCESSFUL)) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_REVIEW_ERROR_1,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_ERROR_1));
    }
    // Check if the test has been evaluated yet
    var checkReview =
        productReviewRepository.checkReview(reqDTO.getProductId(), reqDTO.getOrderId());
    if (checkReview.isPresent()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_REVIEW_ERROR_4,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_ERROR_4));
    }
    // check if the product exist in order detail
    var orderDetails = orderDetailRepository.findAllByOrderId(reqDTO.getOrderId());
    boolean productExistsInOrder =
        orderDetails.stream().anyMatch(od -> od.getProductId().equals(reqDTO.getProductId()));
    if (!productExistsInOrder) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_REVIEW_ERROR_3,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_REVIEW_ERROR_3));
    }
    ProductReview productReview =
        ProductReview.builder()
            .productId(reqDTO.getProductId())
            .orderId(reqDTO.getOrderId())
            .content(reqDTO.getContent())
            .productRating(reqDTO.getProductRating())
            .createdDate(LocalDateTime.now())
            .build();
    productReviewRepository.save(productReview);
    return productReview;
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
  public List<DisplayReviewDTO> getAllByProductId(Long productId) throws CosmeticException {
    return productReviewRepository.getAllProductReviewByProductId(productId);
  }
}
