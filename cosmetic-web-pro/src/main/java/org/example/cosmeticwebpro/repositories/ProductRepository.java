package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.cosmeticwebpro.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  @Transactional
  @Query(
      value =
          "SELECT * "
              + "FROM product p  "
              + "WHERE p.discount_id IS NOT NULL and p.product_status NOT LIKE :productStatus",
      nativeQuery = true)
  List<Product> findAllProductOnSale(@Param("productStatus") String productStatus);

  @Transactional
  @Query(
      value =
          "SELECT * FROM product p "
              + "WHERE p.product_status NOT LIKE :productStatus "
              + "ORDER BY p.count_purchase DESC ",
      nativeQuery = true)
  List<Product> findAllProductBestSeller(@Param("productStatus") String productStatus);

  @Transactional
  @Query(
      value =
          " SELECT * FROM product p  "
              + " WHERE p.product_status NOT LIKE :productStatus"
              + " ORDER BY p.count_view DESC ",
      nativeQuery = true)
  List<Product> findAllProductTheMostView(@Param("productStatus") String productStatus);

  List<Product> findAllByCategoryId(Long categoryId);

  @Query(" SELECT p " +
          " FROM Product p JOIN Category c ON p.categoryId = c.id " +
          " WHERE ( :title is null or p.title like %:title% ) " +
          " AND ( :categoryName is null or c.categoryName LIKE %:categoryName% ) " +
          " ORDER BY " +
          " CASE WHEN :sortCode = '1' THEN p.title END ASC, " +
          " CASE WHEN :sortCode = '2' THEN p.title END DESC, " +
          " CASE WHEN :sortCode = '3' THEN p.currentCost END ASC, " +
          " CASE WHEN :sortCode = '4' THEN p.currentCost END DESC " )
  List<Product> filterByTitleAndCategoryNameAndSortCode(
      @Param("title") String title,
      @Param("categoryName") String categoryName,
      @Param("sortCode") String sortCode);

  @Query(" SELECT p " +
          " FROM Product p " +
          " WHERE ( :title is null or p.title ilike %:title% ) " +
          " ORDER BY " +
          " CASE WHEN :sortCode = '1' THEN p.title END ASC, " +
          " CASE WHEN :sortCode = '2' THEN p.title END DESC, " +
          " CASE WHEN :sortCode = '3' THEN p.currentCost END ASC, " +
          " CASE WHEN :sortCode = '4' THEN p.currentCost END DESC ")
  List<Product> filterByTitleAndSortCode(
      @Param("title") String title,
      @Param("sortCode") String sortCode);

  @Query(
      value =
          "select pm.url_image\n"
              + "from product p join product_image pm on p.id = pm.product_id\n"
              + "where p.id = :productId ",
      nativeQuery = true)
  List<String> findAllImagesByProductId(Long productId);

  @Modifying
  @Transactional
  @Query(value = " UPDATE product  "
      + " SET count_purchase = count_purchase + 1, quantity = quantity - :quantity "
      + " WHERE id = :productId "
  , nativeQuery = true)
  void updateCountPurchase(@Param("productId") Long productId, @Param("quantity") Integer quantity);

}
