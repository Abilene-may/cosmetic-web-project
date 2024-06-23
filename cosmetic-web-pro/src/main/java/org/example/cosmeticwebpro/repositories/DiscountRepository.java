package org.example.cosmeticwebpro.repositories;

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
          "SELECT * FROM Discount d WHERE d.min_amount <= :totalAmount AND d.apply_to = :applyTo "
              + " ORDER BY d.min_amount ASC, d.discount DESC LIMIT 1",
      nativeQuery = true)
  Optional<Discount> findBestDiscountForOrder(
      @Param("totalAmount") double totalAmount, @Param("applyTo") String applyTo);
}
