package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository
    extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Long> {

  @Query(
      value =
          "SELECT * FROM Discount d "
              + "WHERE d.min_amount <= :totalFinalPrice "
              + "AND LOWER(d.apply_to) LIKE LOWER(CONCAT('%', :applyTo, '%'))  "
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

  @Query("UPDATE Discount d SET d.discountStatus = :expired "
      + " WHERE CURRENT_TIMESTAMP > d.toDate ")
  @Modifying
  @Transactional
  void updateExpiredDiscounts(@Param("expired") String expired);

  @Query(
      " UPDATE Discount d SET d.discountStatus = :upcoming "
          + " WHERE CURRENT_TIMESTAMP < d.fromDate ")
  @Modifying
  @Transactional
  void updateUpcomingDiscounts(@Param("upcoming") String upcoming);

  @Query("UPDATE Discount d SET d.discountStatus = :active "
      + " WHERE CURRENT_TIMESTAMP BETWEEN d.fromDate AND d.toDate ")
  @Modifying
  @Transactional
  void updateActiveDiscounts(@Param("active") String active);

  @Query(
      value =
          " SELECT * FROM discount \n"
              + " WHERE apply_to IS NULL \n"
              + "  OR LOWER(TRIM(apply_to)) NOT LIKE :applyTo",
      nativeQuery = true)
  List<Discount> findAllDiscountsFroProduct(@Param("applyTo") String applyTo);
}
