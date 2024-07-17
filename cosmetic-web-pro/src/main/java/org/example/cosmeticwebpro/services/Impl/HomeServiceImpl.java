package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.HomeDisplayDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.repositories.ProductReviewRepository;
import org.example.cosmeticwebpro.services.BrandService;
import org.example.cosmeticwebpro.services.CategoryService;
import org.example.cosmeticwebpro.services.HomeService;
import org.example.cosmeticwebpro.services.ProductReviewService;
import org.example.cosmeticwebpro.services.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {
  private final ProductRepository productRepository;
  private final ProductReviewRepository productReviewRepository;
  private final CategoryService categoryService;
  private final BrandService brandService;
  public HomeServiceImpl(
      @Lazy ProductRepository productRepository,
      @Lazy ProductReviewRepository productReviewRepository,
      @Lazy CategoryService categoryService,
      @Lazy BrandService brandService
  ){
    super();
    this.productRepository =productRepository;
    this.productReviewRepository = productReviewRepository;
    this.categoryService = categoryService;
    this.brandService = brandService;
  }

  @Override
  public HomeDisplayDTO displayHomeScreen() throws CosmeticException {
    // find all product are on sale
    var displayProductDTOS =
        productRepository.findAllProductsForHomeScreen(Constants.PRODUCT_HIDDEN, Constants.ACTIVE);
    var listCategory = categoryService.getAll();
    var listBrand = brandService.getAllBrand();
    return HomeDisplayDTO.builder()
        .displayProductDTOS(displayProductDTOS)
        .categoryList(listCategory)
        .brandList(listBrand)
        .build();
  }

  /*
  function view a product detail
   */
  @Transactional
  @Override
  public ProductDisplayDTO viewAProductDetail(Long productId) throws CosmeticException {
    var product = productRepository.findById(productId);
    if (product.get().getProductStatus().equals(Constants.PRODUCT_HIDDEN)) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN));
    }
    ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
    var displayProductDTO =
        productRepository.findProductDetailByProductIdForUser(
            Constants.PRODUCT_HIDDEN, Constants.ACTIVE, productId);
    List<String> imageUrls = productRepository.findAllImagesByProductId(productId);
    var productReviews = productReviewRepository.getAllProductReviewByProductId(productId);
    productDisplayDTO.setDisplayProductDTO(displayProductDTO.get());
    productDisplayDTO.setProductImages(imageUrls);
    productDisplayDTO.setProductReviews(productReviews);
    // increase view when customers view the product
    var view = product.get().getCountView() + 1;
    product.get().setCountView(view);
    return productDisplayDTO;
  }

  @Override
  public HomeDisplayDTO filterProducts(
      String titleProduct, String categoryName, String sortCode) throws CosmeticException {
    var displayProductDTOS =
        productRepository.searchAllProductsByCondition(
            titleProduct, categoryName, sortCode, Constants.PRODUCT_HIDDEN, Constants.ACTIVE);
    var listCategory = categoryService.getAll();
    var listBrand = brandService.getAllBrand();
    return HomeDisplayDTO.builder()
        .displayProductDTOS(displayProductDTOS)
        .categoryList(listCategory)
        .brandList(listBrand)
        .build();
  }
}
