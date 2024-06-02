package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>,
    JpaSpecificationExecutor<Long> {
  @Query(value = "select * from product_review where product_id = :productId"
  , nativeQuery = true)
  List<ProductReview> findAllByProductId(Long productId);
}
