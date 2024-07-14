package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Discount;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.ProductDiscount;
import org.example.cosmeticwebpro.domains.ProductImage;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.mapper.MapStruct;
import org.example.cosmeticwebpro.models.DisplayProductDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.example.cosmeticwebpro.models.request.ProductUpdateReqDTO;
import org.example.cosmeticwebpro.repositories.BrandRepository;
import org.example.cosmeticwebpro.repositories.CategoryRepository;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.repositories.ProductDiscountRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.repositories.ProductReviewRepository;
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
  private final ProductReviewRepository productReviewRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;
  private final MapStruct mapStruct;
  private final ProductDiscountRepository productDiscountRepository;
  private final DiscountRepository discountRepository;

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
    if (productReqDTO.getDiscountId() != null) {
      ProductDiscount productDiscount =
          ProductDiscount.builder()
              .productId(savedProduct.getId())
              .discountId(productReqDTO.getDiscountId())
              .build();
      productDiscountRepository.save(productDiscount);
    }
    this.updateProductImage(multipartFiles, savedProduct.getId());
  }

  /** view details of 1 product for admin */
  @Override
  public ProductDisplayDTO getByProductId(Long productId) throws CosmeticException {
    var detailByProductId =
        productRepository.findProductDetailByProductId(Constants.ACTIVE, productId);
    if (detailByProductId.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_ID_IS_NOT_EXIST));
    }
    var productReviews = productReviewRepository.findAllByProductId(productId);
    List<String> imageUrls = productRepository.findAllImagesByProductId(productId);
    return ProductDisplayDTO.builder()
        .displayProductDTO(detailByProductId.get())
        .productImages(imageUrls)
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
      ProductUpdateReqDTO updatedProduct, MultipartFile[] multipartFiles, Long[] imageIdDelete)
      throws CosmeticException, IOException {
    Product existingProduct = this.getById(updatedProduct.getId());

    LocalDateTime today = LocalDateTime.now();
    existingProduct.setTitle(updatedProduct.getTitle());
    existingProduct.setDescription(updatedProduct.getDescription());
    existingProduct.setCurrentCost(updatedProduct.getCurrentCost());
    existingProduct.setMadeIn(updatedProduct.getMadeIn());
    existingProduct.setCapacity(updatedProduct.getCapacity());
    existingProduct.setQuantity(updatedProduct.getQuantity());
    existingProduct.setProductStatus(updatedProduct.getProductStatus());
    existingProduct.setModifiedDate(today);
    existingProduct.setBrandId(updatedProduct.getBrandId());
    existingProduct.setCategoryId(updatedProduct.getCategoryId());
    if (updatedProduct.getDiscountId() != null) {
      // find old discount that is still active
      var productDiscounts = productDiscountRepository.findAllByProductId(updatedProduct.getId());
      // delete old discount that is still active
      var findOldDiscount =
          discountRepository.findByListDiscountIdAndStatus(productDiscounts, Constants.ACTIVE);
      var deleteOldDiscount =
          productDiscountRepository.findByDiscountId(findOldDiscount.get().getId());
      productDiscountRepository.delete(deleteOldDiscount.get());

      ProductDiscount productDiscount =
          ProductDiscount.builder()
              .productId(existingProduct.getId())
              .discountId(updatedProduct.getDiscountId())
              .build();
      productDiscountRepository.save(productDiscount);
    }
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
  @Override
  public List<DisplayProductDTO> getAllProductForAdmin() throws CosmeticException {
    // Fetch all products
    return productRepository.findAllProductsForAdmin(Constants.ACTIVE);
  }

  @Transactional
  @Override
  public List<ProductOverviewDTO> productOverviewDTOS(List<Product> products)
      throws CosmeticException {
    // Create a list of ProductOverviewDTO
    List<ProductOverviewDTO> productOverviewDTOs = new ArrayList<>();
    for (Product product : products) {
      ProductOverviewDTO productOverviewDTO = new ProductOverviewDTO();
      var productDTO = mapStruct.mapToProductDTO(product);

      var discount = getDiscountActiveForProduct(product.getId());
      productDTO.setProductDiscount(discount);

      var categoryName = categoryRepository.findCategoryNameById(product.getCategoryId());

      var brandName = brandRepository.findBrandNameById(product.getBrandId());
      productDTO.setCategoryName(categoryName);
      productDTO.setBrandName(brandName);

      List<String> imageUrls = productRepository.findAllImagesByProductId(product.getId());
      productOverviewDTO.setProductDTO(productDTO);
      productOverviewDTO.setImageUrls(imageUrls);
      productOverviewDTOs.add(productOverviewDTO);
    }
    return productOverviewDTOs;
  }

  private void updateProductImage(MultipartFile[] multipartFiles, Long productId)
      throws IOException, CosmeticException {
    for (MultipartFile multipartFile : multipartFiles) {
      BufferedImage bufferedImage;
      bufferedImage = ImageIO.read(multipartFile.getInputStream());
      if (bufferedImage == null) {
        throw new CosmeticException("Invalid image!");
      }
      var result = cloudinaryService.upload(multipartFile);
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

  @Override
  public Discount getDiscountActiveForProduct(Long productId) {
    var productDiscounts = productDiscountRepository.findAllByProductId(productId);
    if (productDiscounts.isEmpty()) {
      return null;
    }
    // Check to see which discount codes work
    var discount =
        discountRepository.findByListDiscountIdAndStatus(productDiscounts, Constants.ACTIVE);
    return discount.get();
  }
}
