package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByOrderById();

    List<ProductImage> findAllByProductsId(Long productId);
}
