package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Long> {

  //  @Query(value = "select * from product where discount_id <> null", nativeQuery = true)
  //  List<Product> findAllProductOnSale();

  @Query(
      value =
          "SELECT p.*, pi.* FROM product p LEFT JOIN product_image pi ON p.id = pi.product_id \n" +
      "WHERE p.discount_id IS NOT NULL",
      nativeQuery = true)
  List<Object[]> findAllProductOnSale();

  @Query(
      value =
          "SELECT p.*, pi.* FROM product p LEFT JOIN product_image pi ON p.id = pi.product_id \n" +
              "ORDER BY p.count_purchase",
      nativeQuery = true)
  List<Object[]> findAllProductBestSeller();

  @Query(
      value =
          "SELECT p.*, pi.* FROM product p LEFT JOIN product_image pi ON p.id = pi.product_id \n" +
              "ORDER BY p.count_view",
      nativeQuery = true)
  List<Object[]> findAllProductTheMostView();
}
