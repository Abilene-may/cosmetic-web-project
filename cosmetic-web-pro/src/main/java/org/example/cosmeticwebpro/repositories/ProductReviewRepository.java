package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.ProductReview;
import org.example.cosmeticwebpro.models.request.DisplayReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>,
    JpaSpecificationExecutor<Long> {
  @Query(value = "select * from product_review where product_id = :productId"
  , nativeQuery = true)
  List<ProductReview> findAllByProductId(Long productId);

  @Transactional
  @Query(
      value =
          "select pr.id as id, \n"
              + "       pr.content as content,\n"
              + "       pr.product_rating as productRating,\n"
              + "       pr.created_date as createdDate,\n"
              + "       pr.product_id as productId,\n"
              + "       pr.order_id as orderId,\n"
              + "       u.username as username\n"
              + "from product_review pr join orders o on pr.order_id = o.id\n"
              + "join users u on o.user_id = u.id\n"
              + "where pr.product_id = :productId\n"
              + "order by pr.created_date",
      nativeQuery = true)
  List<DisplayReviewDTO> getAllProductReviewByProductId(Long productId);


  @Query(value = " select * from product_review where product_id = :productId "
      + "   and orderId = :orderId "
      ,nativeQuery = true
  )
  Optional<ProductReview> checkReview(@Param("productId") Long productId,
      @Param("orderId") Long orderId);
}
