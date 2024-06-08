package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.ProductHistory;
import org.example.cosmeticwebpro.domains.ProductImage;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.example.cosmeticwebpro.repositories.ProductHistoryRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.repositories.ProductReviewRepository;
import org.example.cosmeticwebpro.services.CategoryService;
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
  private final ProductHistoryRepository productHistoryRepository;
  private final ProductReviewRepository productReviewRepository;
  private final CategoryService categoryService;

  @Transactional
  @Override
  public void createProduct(ProductReqDTO productReqDTO, MultipartFile[] multipartFiles)
      throws CosmeticException, IOException {
    if (productReqDTO.getTitle().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_ERROR_1,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_ERROR_1));
    }

    if (productReqDTO.getCurrentCost() == null) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_ERROR_2,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_ERROR_2));
    }
    if (productReqDTO.getMadeIn().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_ERROR_3,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_ERROR_3));
    }
    String productStatus = productReqDTO.getProductStatus();
    if (productReqDTO.getProductStatus().isBlank()) {
      productStatus = Constants.IN_STOCK;
    }

    // check input quantity
    if (productReqDTO.getQuantity() == 0) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_HAS_NO_QUANTITY_YET,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_HAS_NO_QUANTITY_YET));
    }
    Product product =
        Product.builder()
            .title(productReqDTO.getTitle())
            .description(productReqDTO.getDescription())
            .currentCost(productReqDTO.getCurrentCost())
            .madeIn(productReqDTO.getMadeIn())
            .capacity(productReqDTO.getCapacity())
            .quantity(productReqDTO.getQuantity())
            .productStatus(productStatus)
            .countView(0)
            .countPurchase(0)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .brandId(productReqDTO.getBrandId())
            .categoryId(productReqDTO.getCategoryId())
            .build();
    Product savedProduct = productRepository.save(product);

    this.updateProductImage(multipartFiles, savedProduct.getId());
  }

  /** view details of 1 product */
  @Override
  public ProductDisplayDTO getByProductId(Long productId) throws CosmeticException {
    // find information of product
    var product = this.getById(productId);
    var productReviews = productReviewRepository.findAllByProductId(product.getId());
    var productImages = productImageService.getAllByProductId(productId);
    return ProductDisplayDTO.builder()
        .product(product)
        .productImages(productImages)
        .productReviews(productReviews)
        .build();
  }

  /** update all product status */
  @Override
  public List<Product> updateAllProductStatus() throws CosmeticException {
    List<Product> updateProducts;
    // find all product
    List<Product> productList = productRepository.findAll();
    if (productList.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCTS_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCTS_NOT_FOUND));
    }
    // Cập nhật trạng thái của từng sản phẩm
    updateProducts =
        productList.stream()
            .map(
                product -> {
                  // get quantity
                  int quantity = product.getQuantity();
                  // check quantity and update status
                  if (quantity == 0) {
                    product.setProductStatus(Constants.OUT_OF_STOCK);
                  }
                  // update in database
                  productRepository.save(product);
                  return product;
                })
            .collect(Collectors.toList());
    return updateProducts;
  }

  /** API update product */
  @Transactional
  @Override
  public void updateProduct(
      Product updatedProduct, MultipartFile[] multipartFiles, Long[] imageIdDelete)
      throws CosmeticException, IOException {
    Product existingProduct = this.getById(updatedProduct.getId());

    // compare the price want to change and the current price
    // Save history record
    if (!Objects.equals(updatedProduct.getCurrentCost(), existingProduct.getCurrentCost())) {
      var category = categoryService.getById(existingProduct.getId());
      ProductHistory productHistory = getProductHistory(existingProduct, category);
      productHistoryRepository.save(productHistory);
    }
    // in case there is no change in the cost
    existingProduct.setTitle(updatedProduct.getTitle());
    existingProduct.setDescription(updatedProduct.getDescription());
    existingProduct.setCurrentCost(updatedProduct.getCurrentCost());
    existingProduct.setMadeIn(updatedProduct.getMadeIn());
    existingProduct.setCapacity(updatedProduct.getCapacity());
    existingProduct.setQuantity(updatedProduct.getQuantity());
    existingProduct.setProductStatus(updatedProduct.getProductStatus());
    existingProduct.setCountView(updatedProduct.getCountView());
    existingProduct.setCountPurchase(updatedProduct.getCountPurchase());
    existingProduct.setModifiedDate(updatedProduct.getModifiedDate());
    existingProduct.setDisCountId(updatedProduct.getDisCountId());
    existingProduct.setBrandId(updatedProduct.getBrandId());
    existingProduct.setCategoryId(updatedProduct.getCategoryId());

    this.updateProductImage(multipartFiles, existingProduct.getId());
    var product = productRepository.save(existingProduct);

    // delete old images of the product
    if (imageIdDelete == null) return;
    for (Long imageId : imageIdDelete) {
      productImageService.delete((long) imageId.intValue());
    }

    // add images in a product
    for (MultipartFile multipartFile : multipartFiles) {
      BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
      if (bi == null) {
        throw new IOException("Hình ảnh không hợp lệ!");
      }
      Map result = cloudinaryService.upload(multipartFile);
      ProductImage image =
          ProductImage.builder()
              .name((String) result.get("original_filename"))
              .imageUrl((String) result.get("url"))
              .imageId((String) result.get("public_id"))
              .productId(product.getId())
              .build();
      productImageService.save(image);
    }
  }

  /*
  Get all product for admin include hiden product
   */
  public List<ProductOverviewDTO> getAllProductForAdmin() throws CosmeticException {
    // Fetch all products
    List<Product> products = productRepository.findAll();

    // Convert each product to a ProductOverviewDTO
    List<ProductOverviewDTO> productOverviewDTOs =
        products.stream()
            .map(
                product -> {
                  // Fetch images for the product
                  List<ProductImage> productImages = null;
                  try {
                    productImages = productImageService.getAllByProductId(product.getId());
                  } catch (CosmeticException e) {
                    throw new RuntimeException(e);
                  }
                  // Create and return a new ProductOverviewDTO
                  return new ProductOverviewDTO(product, productImages);
                })
            .collect(Collectors.toList());

    return productOverviewDTOs;
  }

  private static ProductHistory getProductHistory(Product existingProduct, Category category) {
    ProductHistory productHistory = new ProductHistory();
    productHistory.setProductId(existingProduct.getId());
    productHistory.setTitle(existingProduct.getTitle());
    productHistory.setDescription(existingProduct.getDescription());
    productHistory.setOldCost(existingProduct.getCurrentCost());
    productHistory.setMadeIn(existingProduct.getMadeIn());
    productHistory.setCapacity(existingProduct.getCapacity());
    productHistory.setQuantity(existingProduct.getQuantity());
    productHistory.setProductStatus(existingProduct.getProductStatus());
    productHistory.setCountPurchase(existingProduct.getCountPurchase());
    productHistory.setCreatedDate(existingProduct.getCreatedDate());
    productHistory.setModifiedDate(existingProduct.getModifiedDate());
    productHistory.setBrandId(existingProduct.getBrandId());
    productHistory.setCategory(category.getCategoryName());
    return productHistory;
  }

  private void updateProductImage(MultipartFile[] multipartFiles, Long productId)
      throws IOException, CosmeticException {
    for (MultipartFile multipartFile : multipartFiles) {
      BufferedImage bufferedImage;
      bufferedImage = ImageIO.read(multipartFile.getInputStream());
      if (bufferedImage == null) {
        throw new CosmeticException("Invalid image!");
      }
      Map result = cloudinaryService.upload(multipartFile);
      ProductImage productImage =
          ProductImage.builder()
              .name((String) result.get("original_filename"))
              .imageUrl((String) result.get("url"))
              .imageId((String) result.get("public_id"))
              .productId(productId)
              .build();
      productImageService.save(productImage);
    }
  }

  public Product getById(Long id) throws CosmeticException {
    var product = productRepository.findById(id);
    if (product.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST));
    }
    return product.get();
  }
}
