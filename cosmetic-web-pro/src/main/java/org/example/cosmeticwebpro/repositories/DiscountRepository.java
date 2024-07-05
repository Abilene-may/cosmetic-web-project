package org.example.cosmeticwebpro.repositories;

import java.util.Date;
import java.util.Optional;
import javax.xml.crypto.Data;
import org.example.cosmeticwebpro.domains.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository
    extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Long> {

  @Query(
      value = "SELECT * FROM Discount d " +
          "WHERE d.min_amount <= :totalAmount " +
          "AND d.apply_to = :applyTo " +
          "AND :today BETWEEN d.from_date AND d.to_date " +
          "AND (EXTRACT(HOUR FROM :today) > d.start_hour " +
          "OR (EXTRACT(HOUR FROM :today) = d.start_hour AND EXTRACT(MINUTE FROM :today) >= d.start_minute)) " +
          "AND (EXTRACT(HOUR FROM :today) < d.end_hour " +
          "OR (EXTRACT(HOUR FROM :today) = d.end_hour AND EXTRACT(MINUTE FROM :today) <= d.end_minute)) " +
          "ORDER BY d.min_amount DESC " +
          "LIMIT 1",
      nativeQuery = true)
  Optional<Discount> findBestDiscountForOrder(
      @Param("totalAmount") double totalAmount, @Param("applyTo") String applyTo, @Param("today") Date today);
}
