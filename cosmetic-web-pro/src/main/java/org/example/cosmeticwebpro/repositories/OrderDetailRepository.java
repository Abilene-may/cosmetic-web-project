package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>,
    JpaSpecificationExecutor<Long> {
  @Query(value = " select * from order_detail order by modified_date DESC"
  , nativeQuery = true)
  List<OrderDetail> findAllByOrderId(Long orderId);
}
