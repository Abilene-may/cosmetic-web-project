package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayProductDTO;
import org.example.cosmeticwebpro.models.DisplayProductForAdminDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.example.cosmeticwebpro.models.request.ProductUpdateReqDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ProductService {
  void createProduct(ProductReqDTO productReqDTO, MultipartFile[] multipartFiles)
      throws CosmeticException, IOException;

  DisplayProductForAdminDTO getByProductId(Long productId) throws CosmeticException;

  List<Product> updateAllProductStatus() throws CosmeticException;

  void updateProduct(ProductUpdateReqDTO updatedProduct, MultipartFile[] multipartFiles, Long[] imageIdDelete)
      throws CosmeticException, IOException;

  List<DisplayProductDTO> getAllProductForAdmin() throws CosmeticException;

  List<ProductOverviewDTO> productOverviewDTOS(List<Product> products) throws CosmeticException;

  Discount getDiscountActiveForProduct(Long productId);

}
