package org.example.cosmeticwebpro.repositories;

import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>,
    JpaSpecificationExecutor<Long> {

  @Query(
      value = "select * from cart_detail where cart_id = :cartId order by modified_date",
      nativeQuery = true)
  List<CartDetail> findAllByCartId(Long cartId);

  @Query(
      value = "select * from cart_detail where product_id = :productId and cart_id = :cartId",
      nativeQuery = true)
  Optional<CartDetail> findAllByProductId(Long productId, Long cartId);
}
