package org.example.cosmeticwebpro.services;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ProductService {
  void createProduct(ProductReqDTO productReqDTO, MultipartFile[] multipartFiles)
      throws CosmeticException, IOException;

  ProductDisplayDTO getByProductId(Long productId) throws CosmeticException;

  List<Product> updateAllProductStatus() throws CosmeticException;

  void updateProduct(Product updatedProduct, MultipartFile[] multipartFiles, Long[] imageIdDelete)
      throws CosmeticException, IOException;

  List<ProductOverviewDTO> getAllProductForAdmin() throws CosmeticException;
}
