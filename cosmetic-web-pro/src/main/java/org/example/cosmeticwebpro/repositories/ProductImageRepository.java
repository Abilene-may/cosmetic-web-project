package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<Long> {
    List<ProductImage> findByOrderById();

    List<ProductImage> findProductImagesByProductId(Long productId);

    @Query(
        value =
            "select pm.url_image "
                + " from product p join product_image pm on p.id = pm.product_id "
                + " where p.id = :productId ",
        nativeQuery = true)
    List<String> findAllImageUrlByProductId(Long productId);

}
