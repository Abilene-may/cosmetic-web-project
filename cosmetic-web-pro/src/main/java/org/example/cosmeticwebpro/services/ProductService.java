package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    void createProduct(ProductReqDTO productReqDTO, MultipartFile[] multipartFiles) throws CosmeticException, IOException;

    Product getByProductId(Long productId, String roleName) throws CosmeticException;

    List<Product> updateAllProductStatus() throws CosmeticException;
}
