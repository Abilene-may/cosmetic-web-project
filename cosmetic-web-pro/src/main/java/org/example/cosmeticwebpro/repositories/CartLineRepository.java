package org.example.cosmeticwebpro.repositories;

import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CartLineRepository extends JpaRepository<CartLine, Long>,
    JpaSpecificationExecutor<Long> {

  @Query(
      value = "select * from cart_line where cart_id = :cartId order by modified_date desc ",
      nativeQuery = true)
  List<CartLine> findAllByCartId(Long cartId);

  @Query(
      value = "select * from cart_line where product_id = :productId and cart_id = :cartId",
      nativeQuery = true)
  Optional<CartLine> findAllByProductId(Long productId, Long cartId);

  @Query(value = "select cd.*\n"
      + "FROM users u JOIN cart c ON u.id = c.user_id\n"
      + "JOIN cart_line cd ON c.id = cd.cart_id\n"
      + "WHERE u.id = :userId "
  , nativeQuery = true)
  List<CartLine> findAllByUserId(Long userId);
}
