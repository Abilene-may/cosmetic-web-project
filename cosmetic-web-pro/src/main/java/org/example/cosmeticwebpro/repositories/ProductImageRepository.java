package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<Long> {
    List<ProductImage> findByOrderById();

    List<ProductImage> findProductImagesByProductId(Long productId);

    @Query(
        value =
            "select * "
                + " from product_image  "
                + " where product_id = :productId ",
        nativeQuery = true)
    List<ProductImage> findAllByProductId(@Param("productId") Long productId);

}
