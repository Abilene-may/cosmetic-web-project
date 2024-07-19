package org.example.cosmeticwebpro.repositories;

import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Order;
import org.example.cosmeticwebpro.models.StatisticalDTO;
import org.example.cosmeticwebpro.models.UserPotentialDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Long> {
  @Query(value = " select * from orders where user_id = :userId order by order_date desc "
  , nativeQuery = true)
  List<Order> findAllByUserId(Long userId);

  Optional<Order> findByUserIdAndId(Long userId, Long orderId);

  List<Order> findAllByStatus(String status);

  @Query(
      value =
          "SELECT\n"
              + "    COUNT(*) AS totalOrders,\n"
              + "    COUNT(CASE WHEN status = 'Đơn hàng đã bị hủy.' THEN 1 END) AS canceledOrders,\n"
              + "    COUNT(CASE WHEN status = 'Giao hàng thành công.' THEN 1 END) AS orderSuccessful,\n"
              + "    COUNT(CASE WHEN status = 'Đặt hàng thành công.' THEN 1 END) AS pendingOrders,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 1 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfJanuary,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 2 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfFebruary,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 3 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfMarch,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 4 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfApril,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 5 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfMay,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 6 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfJune,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 7 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfJuly,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 8 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfAugust,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 9 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfSeptember,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 10 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfOctober,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 11 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfNovember,\n"
              + "    COALESCE(SUM(CASE WHEN EXTRACT(MONTH FROM order_date) = 12 AND EXTRACT(YEAR FROM order_date) = :year AND status = 'Giao hàng thành công.' THEN total_cost END), 0) AS totalRevenueOfDecember\n"
              + "FROM orders\n"
              + "WHERE EXTRACT(YEAR FROM order_date) = :year",
      nativeQuery = true)
  Optional<StatisticalDTO> statisticalOrder(Integer year);

  @Query(
      value =
          "SELECT \n"
              + "    u.id AS userId,\n"
              + "    u.username AS username,\n"
              + "    COUNT(o.id) AS totalOrders,\n"
              + "    COALESCE(SUM(od.product_cost * od.quantity), 0) AS totalSpend\n"
              + "FROM \n"
              + "    users u\n"
              + "    LEFT JOIN orders o ON u.id = o.user_id\n"
              + "    LEFT JOIN order_detail od ON o.id = od.order_id\n"
              + "WHERE \n"
              + "    o.status = 'Giao hàng thành công.'\n"
              + "GROUP BY \n"
              + "    u.id, u.username\n"
              + "ORDER BY \n"
              + "    totalSpend DESC;",
      nativeQuery = true)
  List<UserPotentialDTO> findALlUserPotential();

  @Query(value = " select * from orders order by order_date desc "
      , nativeQuery = true
  )
  List<Order> findAllForAdmin();
}
