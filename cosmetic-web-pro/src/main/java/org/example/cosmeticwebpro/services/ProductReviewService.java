package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.ProductReviewReqDTO;

public interface ProductReviewService {
  ProductReview create(ProductReviewReqDTO reqDTO) throws CosmeticException;

  ProductReview getById(Long productReviewId) throws CosmeticException;

  List<ProductReview> getAllByProductId(Long productId) throws CosmeticException;
}
