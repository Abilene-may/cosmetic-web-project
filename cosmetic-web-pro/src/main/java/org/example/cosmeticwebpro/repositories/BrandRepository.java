package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.Brand;
import org.example.cosmeticwebpro.models.RevenueOfBrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Long> {

  @Query( value = " select name from brand where id = :brandId "
      , nativeQuery = true)
  String findBrandNameById(Long brandId);

  @Query(value = "SELECT\n"
      + "    b.id AS brandId,\n"
      + "    b.name AS brandName,\n"
      + "    COALESCE(SUM(od.product_cost * od.quantity), 0) AS totalRevenueOfYear\n"
      + "FROM brand b\n"
      + "JOIN product p ON b.id = p.brand_id\n"
      + "JOIN order_detail od ON p.id = od.product_id\n"
      + "JOIN orders o ON od.order_id = o.id\n"
      + "WHERE EXTRACT(YEAR FROM o.order_date) = :year "
      + "GROUP BY "
      + "    b.id, b.name "
      + " LIMIT 10 "
  , nativeQuery = true)
  List<RevenueOfBrandDTO> findAllRevenueOfBrandsByYear(Integer year);
}
