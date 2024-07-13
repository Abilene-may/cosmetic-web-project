package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Brand;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.HomeDisplayDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.BrandService;
import org.example.cosmeticwebpro.services.CartService;
import org.example.cosmeticwebpro.services.CategoryService;
import org.example.cosmeticwebpro.services.HomeService;
import org.example.cosmeticwebpro.services.ProductService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
  private final ProductRepository productRepository;
  private final ProductService productService;
  private final CartService cartService;
  private final CategoryService categoryService;
  private final BrandService brandService;

  @Override
  public HomeDisplayDTO displayHomeScreen() throws CosmeticException {
    // find all product are on sale
    var listProductBestSeller =
        productRepository.findAllProductBestSeller(Constants.PRODUCT_HIDDEN);
    var displayListBestSeller = productService.productOverviewDTOS(listProductBestSeller);
    var listCategory = categoryService.getAll();
    var listBrand = brandService.getAllBrand();
    return HomeDisplayDTO.builder()
        .listProductBestSeller(displayListBestSeller)
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
    var productDisplayDTO = productService.getByProductId(productId);
    var product = productDisplayDTO.getProductDTO();
    // check for hidden product
    if (product.getProductStatus().equals(Constants.PRODUCT_HIDDEN)) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN));
    }
    if (product.getCategoryId() != null) {
      var category = categoryService.getById(product.getCategoryId());
      productDisplayDTO.setCategory(category);
    }
    if (product.getBrandId() != null) {
      var brand = brandService.getById(product.getBrandId());
      productDisplayDTO.setBrand(brand);
    }
    // increase view when customers view the product
    var view = product.getCountView() + 1;
    product.setCountView(view);
    return productDisplayDTO;
  }

  @Override
  public List<ProductOverviewDTO> filterProducts(
      String titleProduct, String categoryName, String sortCode) throws CosmeticException {
    List<Product> products = new ArrayList<>();
    if (categoryName == null || categoryName.isBlank()) {
      products = productRepository.filterByTitleAndSortCode(titleProduct, sortCode);
    } else {
      products =
          productRepository.filterByTitleAndCategoryNameAndSortCode(
              titleProduct, categoryName, sortCode);
    }
    return productService.productOverviewDTOS(products);
  }
}
