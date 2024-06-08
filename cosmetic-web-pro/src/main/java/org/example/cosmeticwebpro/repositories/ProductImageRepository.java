package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<Long> {
    List<ProductImage> findByOrderById();

    List<ProductImage> findProductImagesByProductId(Long productId);
}
