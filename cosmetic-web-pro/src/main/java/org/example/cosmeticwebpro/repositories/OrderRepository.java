package org.example.cosmeticwebpro.repositories;

import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Long> {
  @Query(value = " select * from orders where user_id = :userId order by order_date desc "
  , nativeQuery = true)
  List<Order> findAllByUserId(Long userId);

  Optional<Order> findByUserIdAndId(Long userId, Long orderId);

  List<Order> findAllByStatus(String status);
}
