package org.example.cosmeticwebpro.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository
    extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Long> {

  @Query(
      value =
          "SELECT * FROM Discount d "
              + "WHERE d.min_amount <= :totalFinalPrice "
              + "AND d.apply_to = :applyTo "
              + "AND CAST(:today AS timestamp) BETWEEN d.from_date AND d.to_date "
              + "AND d.discount_status = :discountStatus "
              + "ORDER BY d.min_amount DESC "
              + "LIMIT 1",
      nativeQuery = true)
  Optional<Discount> findBestDiscountForOrder(
      @Param("totalFinalPrice") double totalFinalPrice,
      @Param("discountStatus") String discountStatus,
      @Param("applyTo") String applyTo,
      @Param("today") LocalDateTime today);

  @Query(
      value =
          " SELECT * FROM discount d "
              + " WHERE d.id IN (:discountIds) AND d.discount_status = :status "
              + " LIMIT 1",
      nativeQuery = true)
  Optional<Discount> findByListDiscountIdAndStatus(
      @Param("discountIds") List<Long> discountIds, @Param("status") String status);

  @Query(
      value =
          " SELECT * FROM discount d "
              + " WHERE d.id = :discountId AND d.discount_status = :status "
      ,nativeQuery = true)
  Optional<Discount> findByIdAndStatus(
      @Param("discountId") Long discountId, @Param("status") String status);
}
