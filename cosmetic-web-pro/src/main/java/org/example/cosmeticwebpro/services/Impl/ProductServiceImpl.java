package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.ProductImage;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.CloudinaryService;
import org.example.cosmeticwebpro.services.ProductImageService;
import org.example.cosmeticwebpro.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductImageService productImageService;

    @Transactional
    @Override
    public void createProduct(ProductReqDTO productReqDTO, MultipartFile[] multipartFiles) throws CosmeticException, IOException {
        Product product = Product.builder()
                .title(productReqDTO.getTitle())
                .description(productReqDTO.getDescription())
                .currentCost(productReqDTO.getCurrentCost())
                .category(productReqDTO.getCategory())
                .madeIn(productReqDTO.getMadeIn())
                .capacity(productReqDTO.getCapacity())
                .quantity(productReqDTO.getQuantity())
                .productStatus(Constants.IN_STOCK)
                .countView(0)
                .countPurchase(0)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .brandId(productReqDTO.getBrandId())
                .build();
        Product savedProduct = productRepository.save(product);

        for (MultipartFile multipartFile : multipartFiles) {
            BufferedImage bufferedImage;
            bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if (bufferedImage == null) {
                throw new CosmeticException("Invalid image!");
            }
            Map result = cloudinaryService.upload(multipartFile);
            ProductImage productImage = ProductImage.builder()
                    .name((String) result.get("original_filename"))
                    .imageUrl((String) result.get("url"))
                    .imageId((String) result.get("public_id"))
                    .products(savedProduct)
                    .build();
            productImageService.save(productImage);
        }
    }
}
