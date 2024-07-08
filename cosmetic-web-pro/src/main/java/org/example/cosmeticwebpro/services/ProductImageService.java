package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.domains.ProductImage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.cosmeticwebpro.exceptions.CosmeticException;

public interface ProductImageService {
  List<ProductImage> list();

  Optional<ProductImage> getOne(Long id);

  void save(ProductImage productImage);

  void delete(Long id) throws IOException;

  boolean exists(Long id);

  List<ProductImage> getAllByProductId(Long productId) throws CosmeticException;

  List<ProductImage> getAll();
}
