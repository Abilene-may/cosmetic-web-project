package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  @Transactional
  @Query(
      value = "SELECT * " +
          "FROM product p  " +
          "WHERE p.discount_id IS NOT NULL and p.product_status NOT LIKE :productStatus",
      nativeQuery = true)
  List<Product> findAllProductOnSale(@Param("productStatus") String productStatus);


  @Transactional
  @Query(
      value = "SELECT * FROM product p "
          + "WHERE p.product_status NOT LIKE :productStatus " +
          "ORDER BY p.count_purchase DESC ",
      nativeQuery = true)
  List<Product> findAllProductBestSeller(@Param("productStatus") String productStatus);

  @Transactional
  @Query(
      value = " SELECT * FROM product p  "
          + " WHERE p.product_status NOT LIKE :productStatus"
          + " ORDER BY p.count_view DESC ",
      nativeQuery = true)
  List<Product> findAllProductTheMostView(@Param("productStatus") String productStatus);
}
