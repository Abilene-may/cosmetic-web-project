package org.example.cosmeticwebpro.repositories;

import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductDiscountRepository
    extends JpaRepository<ProductDiscount, Long>, JpaSpecificationExecutor<Long> {

  @Query(
      value = " SELECT discount_id FROM product_discount "
          + " WHERE product_id = :productId ",
      nativeQuery = true)
  List<Long> findAllByProductId(Long productId);

  Optional<ProductDiscount> findByDiscountId(Long discountId);
}
