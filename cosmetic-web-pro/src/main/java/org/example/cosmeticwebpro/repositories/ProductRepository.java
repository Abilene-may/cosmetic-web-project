package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.models.DisplayProductDTO;
import org.example.cosmeticwebpro.models.ProductStatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id,\n"
              + "    p.title AS title,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.count_purchase AS countPurchase\n"
              + " FROM product p\n"
              + " WHERE p.quantity <= 10\n"
              + " ORDER BY p.quantity ASC\n"
              + " LIMIT 10",
      nativeQuery = true)
  List<ProductStatisticDTO> findAllProductsOutOfStock();

  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id, \n"
              + "    p.title AS title, \n"
              + "    p.description AS description,\n"
              + "    p.current_cost AS currentCost,\n"
              + "    p.made_in AS madeIn,\n"
              + "    p.capacity AS capacity,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.product_status AS productStatus,\n"
              + "    p.count_view AS countView,\n"
              + "    p.count_purchase AS countPurchase,\n"
              + "    p.created_date AS createdDate,\n"
              + "    p.modified_date AS modifiedDate,\n"
              + "    p.brand_id AS brandId,\n"
              + "    p.category_id AS categoryId,\n"
              + "    MAX(pm.url_image) AS imageUrl,\n"
              + "    COALESCE(MAX(d.discount_percent), 0) AS percentDiscount,\n"
              + "    COALESCE(c.category_name, null) AS categoryName,\n"
              + "    COALESCE(b.name, null) AS brandName,\n"
              + "    COALESCE(COUNT(r.id), 0) AS totalRating,\n"
              + "    COALESCE(AVG(r.product_rating), 0) AS averageRating\n"
              + "FROM \n"
              + "    product p \n"
              + "    LEFT JOIN (\n"
              + "        SELECT product_id, MAX(url_image) AS url_image \n"
              + "        FROM product_image \n"
              + "        GROUP BY product_id \n"
              + "    ) pm ON p.id = pm.product_id \n"
              + "    LEFT JOIN category c ON c.id = p.category_id \n"
              + "    LEFT JOIN brand b ON b.id = p.brand_id \n"
              + "    LEFT JOIN (\n"
              + "        SELECT \n"
              + "            product_id, \n"
              + "            discount_percent \n"
              + "        FROM \n"
              + "            discount d1 \n"
              + "            JOIN product_discount pd ON d1.id = pd.discount_id \n"
              + "        WHERE \n"
              + "            d1.discount_status = :discountStatus \n"
              + "    ) d ON p.id = d.product_id \n"
              + "    LEFT JOIN product_review r ON p.id = r.product_id \n"
              + "WHERE \n"
              + "    p.id IS NOT NULL\n"
              + "    AND p.product_status != :productStatus \n"
              + "    AND (:titleProduct IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :titleProduct, '%'))) \n"
              + "    AND (:categoryName IS NULL OR LOWER(c.category_name) LIKE LOWER(CONCAT('%', :categoryName, '%'))) \n"
              + "GROUP BY \n"
              + "    p.id, \n"
              + "    p.title, \n"
              + "    p.description,\n"
              + "    p.current_cost,\n"
              + "    p.made_in,\n"
              + "    p.capacity,\n"
              + "    p.quantity,\n"
              + "    p.product_status,\n"
              + "    p.count_view,\n"
              + "    p.count_purchase,\n"
              + "    p.created_date,\n"
              + "    p.modified_date,\n"
              + "    p.brand_id,\n"
              + "    p.category_id,\n"
              + "    c.category_name,\n"
              + "    b.name \n"
              + "ORDER BY \n"
              + "    CASE WHEN :sortCode = '1' THEN p.title END ASC, \n"
              + "    CASE WHEN :sortCode = '2' THEN p.title END DESC, \n"
              + "    CASE WHEN :sortCode = '3' THEN p.current_cost END ASC, \n"
              + "    CASE WHEN :sortCode = '4' THEN p.current_cost END DESC",
      nativeQuery = true
  )
  List<DisplayProductDTO> searchAllProductsByCondition(
      @Param("titleProduct") String titleProduct,
      @Param("categoryName") String categoryName,
      @Param("sortCode") String sortCode,
      @Param("productStatus") String productStatus,
      @Param("discountStatus") String discountStatus
  );

  @Transactional
  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id, \n"
              + "    p.title AS title, \n"
              + "    p.description AS description,\n"
              + "    p.current_cost AS currentCost,\n"
              + "    p.made_in AS madeIn,\n"
              + "    p.capacity AS capacity,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.product_status AS productStatus,\n"
              + "    p.count_view AS countView,\n"
              + "    p.count_purchase AS countPurchase,\n"
              + "    p.created_date AS createdDate,\n"
              + "    p.modified_date AS modifiedDate,\n"
              + "    p.brand_id AS brandId,\n"
              + "    p.category_id AS categoryId,\n"
              + "    MAX(pm.url_image) AS imageUrl,\n"
              + "    COALESCE(MAX(d.discount_percent), 0) AS percentDiscount,\n"
              + "    COALESCE(c.category_name, null) AS categoryName,\n"
              + "    COALESCE(b.name, null) AS brandName,\n"
              + "    COALESCE(COUNT(r.id), 0) AS totalRating,\n"
              + "    COALESCE(AVG(r.product_rating), 0) AS averageRating\n"
              + "FROM \n"
              + "    product p \n"
              + "    LEFT JOIN (\n"
              + "        SELECT product_id, MAX(url_image) AS url_image \n"
              + "        FROM product_image \n"
              + "        GROUP BY product_id \n"
              + "    ) pm ON p.id = pm.product_id \n"
              + "    LEFT JOIN category c ON c.id = p.category_id \n"
              + "    LEFT JOIN brand b ON b.id = p.brand_id \n"
              + "    LEFT JOIN (\n"
              + "        SELECT \n"
              + "            product_id, \n"
              + "            discount_percent \n"
              + "        FROM \n"
              + "            discount d1 \n"
              + "            JOIN product_discount pd ON d1.id = pd.discount_id \n"
              + "        WHERE \n"
              + "            d1.discount_status = :discountStatus \n"
              + "    ) d ON p.id = d.product_id \n"
              + "    LEFT JOIN product_review r ON p.id = r.product_id \n"
              + "WHERE \n"
              + "    p.id IS NOT NULL\n"
              + "    AND p.product_status != :productStatus \n"
              + "GROUP BY \n"
              + "    p.id, \n"
              + "    p.title, \n"
              + "    p.description,\n"
              + "    p.current_cost,\n"
              + "    p.made_in,\n"
              + "    p.capacity,\n"
              + "    p.quantity,\n"
              + "    p.product_status,\n"
              + "    p.count_view,\n"
              + "    p.count_purchase,\n"
              + "    p.created_date,\n"
              + "    p.modified_date,\n"
              + "    p.brand_id,\n"
              + "    p.category_id,\n"
              + "    c.category_name,\n"
              + "    b.name ",
      nativeQuery = true
  )
  List<DisplayProductDTO> findAllProductsForHomeScreen(
      @Param("productStatus") String productStatus,
      @Param("discountStatus") String discountStatus
  );

  @Transactional
  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id, \n"
              + "    p.title AS title, \n"
              + "    p.description AS description,\n"
              + "    p.current_cost AS currentCost,\n"
              + "    p.made_in AS madeIn,\n"
              + "    p.capacity AS capacity,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.product_status AS productStatus,\n"
              + "    p.count_view AS countView,\n"
              + "    p.count_purchase AS countPurchase,\n"
              + "    p.created_date AS createdDate,\n"
              + "    p.modified_date AS modifiedDate,\n"
              + "    p.brand_id AS brandId,\n"
              + "    p.category_id AS categoryId,\n"
              + "    MAX(pm.url_image) AS imageUrl,\n"
              + "    COALESCE(MAX(d.discount_percent), 0) AS percentDiscount,\n"
              + "    COALESCE(c.category_name, null) AS categoryName,\n"
              + "    COALESCE(b.name, null) AS brandName,\n"
              + "    COALESCE(COUNT(r.id), 0) AS totalRating,\n"
              + "    COALESCE(AVG(r.product_rating), 0) AS averageRating\n"
              + "FROM \n"
              + "    product p \n"
              + "    LEFT JOIN (\n"
              + "        SELECT product_id, MAX(url_image) AS url_image \n"
              + "        FROM product_image \n"
              + "        GROUP BY product_id \n"
              + "    ) pm ON p.id = pm.product_id \n"
              + "    LEFT JOIN category c ON c.id = p.category_id \n"
              + "    LEFT JOIN brand b ON b.id = p.brand_id \n"
              + "    LEFT JOIN (\n"
              + "        SELECT \n"
              + "            product_id, \n"
              + "            discount_percent \n"
              + "        FROM \n"
              + "            discount d1 \n"
              + "            JOIN product_discount pd ON d1.id = pd.discount_id \n"
              + "        WHERE \n"
              + "            d1.discount_status = :discountStatus \n"
              + "    ) d ON p.id = d.product_id \n"
              + "    LEFT JOIN product_review r ON p.id = r.product_id \n"
              + "WHERE \n"
              + "    p.id IS NOT NULL\n"
              + "    AND p.id = :productId "
              + "    AND p.product_status != :productStatus \n"
              + "GROUP BY \n"
              + "    p.id, \n"
              + "    p.title, \n"
              + "    p.description,\n"
              + "    p.current_cost,\n"
              + "    p.made_in,\n"
              + "    p.capacity,\n"
              + "    p.quantity,\n"
              + "    p.product_status,\n"
              + "    p.count_view,\n"
              + "    p.count_purchase,\n"
              + "    p.created_date,\n"
              + "    p.modified_date,\n"
              + "    p.brand_id,\n"
              + "    p.category_id,\n"
              + "    c.category_name,\n"
              + "    b.name " ,
      nativeQuery = true
  )
  Optional<DisplayProductDTO> findProductDetailByProductIdForUser(
      @Param("productStatus") String productStatus,
      @Param("discountStatus") String discountStatus,
      @Param("productId") Long productId
  );

  @Transactional
  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id, \n"
              + "    p.title AS title, \n"
              + "    p.description AS description,\n"
              + "    p.current_cost AS currentCost,\n"
              + "    p.made_in AS madeIn,\n"
              + "    p.capacity AS capacity,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.product_status AS productStatus,\n"
              + "    p.count_view AS countView,\n"
              + "    p.count_purchase AS countPurchase,\n"
              + "    p.created_date AS createdDate,\n"
              + "    p.modified_date AS modifiedDate,\n"
              + "    p.brand_id AS brandId,\n"
              + "    p.category_id AS categoryId,\n"
              + "    MAX(pm.url_image) AS imageUrl,\n"
              + "    COALESCE(MAX(d.discount_percent), 0) AS percentDiscount,\n"
              + "    COALESCE(c.category_name, null) AS categoryName,\n"
              + "    COALESCE(b.name, null) AS brandName,\n"
              + "    COALESCE(COUNT(r.id), 0) AS totalRating,\n"
              + "    COALESCE(AVG(r.product_rating), 0) AS averageRating\n"
              + "FROM \n"
              + "    product p \n"
              + "    LEFT JOIN (\n"
              + "        SELECT product_id, MAX(url_image) AS url_image \n"
              + "        FROM product_image \n"
              + "        GROUP BY product_id \n"
              + "    ) pm ON p.id = pm.product_id \n"
              + "    LEFT JOIN category c ON c.id = p.category_id \n"
              + "    LEFT JOIN brand b ON b.id = p.brand_id \n"
              + "    LEFT JOIN (\n"
              + "        SELECT \n"
              + "            product_id, \n"
              + "            discount_percent \n"
              + "        FROM \n"
              + "            discount d1 \n"
              + "            JOIN product_discount pd ON d1.id = pd.discount_id \n"
              + "        WHERE \n"
              + "            d1.discount_status = :discountStatus \n"
              + "    ) d ON p.id = d.product_id \n"
              + "    LEFT JOIN product_review r ON p.id = r.product_id \n"
              + "WHERE \n"
              + "    p.id IS NOT NULL\n"
              + "GROUP BY \n"
              + "    p.id, \n"
              + "    p.title, \n"
              + "    p.description,\n"
              + "    p.current_cost,\n"
              + "    p.made_in,\n"
              + "    p.capacity,\n"
              + "    p.quantity,\n"
              + "    p.product_status,\n"
              + "    p.count_view,\n"
              + "    p.count_purchase,\n"
              + "    p.created_date,\n"
              + "    p.modified_date,\n"
              + "    p.brand_id,\n"
              + "    p.category_id,\n"
              + "    c.category_name,\n"
              + "    b.name ",
      nativeQuery = true
  )
  List<DisplayProductDTO> findAllProductsForAdmin(
      @Param("discountStatus") String discountStatus
  );

  @Transactional
  @Query(
      value =
          "SELECT \n"
              + "    p.id AS id, \n"
              + "    p.title AS title, \n"
              + "    p.description AS description,\n"
              + "    p.current_cost AS currentCost,\n"
              + "    p.made_in AS madeIn,\n"
              + "    p.capacity AS capacity,\n"
              + "    p.quantity AS quantity,\n"
              + "    p.product_status AS productStatus,\n"
              + "    p.count_view AS countView,\n"
              + "    p.count_purchase AS countPurchase,\n"
              + "    p.created_date AS createdDate,\n"
              + "    p.modified_date AS modifiedDate,\n"
              + "    p.brand_id AS brandId,\n"
              + "    p.category_id AS categoryId,\n"
              + "    MAX(pm.url_image) AS imageUrl,\n"
              + "    COALESCE(MAX(d.discount_percent), 0) AS percentDiscount,\n"
              + "    COALESCE(c.category_name, null) AS categoryName,\n"
              + "    COALESCE(b.name, null) AS brandName,\n"
              + "    COALESCE(COUNT(r.id), 0) AS totalRating,\n"
              + "    COALESCE(AVG(r.product_rating), 0) AS averageRating\n"
              + "FROM \n"
              + "    product p \n"
              + "    LEFT JOIN (\n"
              + "        SELECT product_id, MAX(url_image) AS url_image \n"
              + "        FROM product_image \n"
              + "        GROUP BY product_id \n"
              + "    ) pm ON p.id = pm.product_id \n"
              + "    LEFT JOIN category c ON c.id = p.category_id \n"
              + "    LEFT JOIN brand b ON b.id = p.brand_id \n"
              + "    LEFT JOIN (\n"
              + "        SELECT \n"
              + "            product_id, \n"
              + "            discount_percent \n"
              + "        FROM \n"
              + "            discount d1 \n"
              + "            JOIN product_discount pd ON d1.id = pd.discount_id \n"
              + "        WHERE \n"
              + "            d1.discount_status = :discountStatus \n"
              + "    ) d ON p.id = d.product_id \n"
              + "    LEFT JOIN product_review r ON p.id = r.product_id \n"
              + "WHERE \n"
              + "    p.id IS NOT NULL\n"
              + "    AND p.id = :productId "
              + "GROUP BY \n"
              + "    p.id, \n"
              + "    p.title, \n"
              + "    p.description,\n"
              + "    p.current_cost,\n"
              + "    p.made_in,\n"
              + "    p.capacity,\n"
              + "    p.quantity,\n"
              + "    p.product_status,\n"
              + "    p.count_view,\n"
              + "    p.count_purchase,\n"
              + "    p.created_date,\n"
              + "    p.modified_date,\n"
              + "    p.brand_id,\n"
              + "    p.category_id,\n"
              + "    c.category_name,\n"
              + "    b.name " ,
      nativeQuery = true
  )
  Optional<DisplayProductDTO> findProductDetailByProductId(
      @Param("discountStatus") String discountStatus,
      @Param("productId") Long productId
  );

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
