package org.example.cosmeticwebpro.services.Impl;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.HomeDisplayDTO;
import org.example.cosmeticwebpro.models.ProductDisplayDTO;
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
    var listProductOnSale = productRepository.findAllProductOnSale(Constants.PRODUCT_HIDDEN);
    var displayListOneSale = productService.productOverviewDTOS(listProductOnSale);
    var listProductBestSeller = productRepository.findAllProductBestSeller(Constants.PRODUCT_HIDDEN);
    var displayListBestSeller = productService.productOverviewDTOS(listProductBestSeller);
    var listProductTheMostView = productRepository.findAllProductTheMostView(Constants.PRODUCT_HIDDEN);
    var displayListTheMostView = productService.productOverviewDTOS(listProductTheMostView);
    Integer totalQuantityCart = 0;
    var listCategory = categoryService.getAll();
    var listBrand = brandService.getAllBrand();
    return HomeDisplayDTO.builder()
        .totalQuantityCart(totalQuantityCart)
        .listProductOnSale(displayListOneSale)
        .listProductBestSeller(displayListBestSeller)
        .listProductTheMostView(displayListTheMostView)
        .categoryList(listCategory)
        .brandList(listBrand).build();
  }

  public List<Product> checkNullPointer(List<Product> productList){
    if (productList == null) {
      return Collections.emptyList();
    }
    return productList;
  }

  /*
  function view a product detail
   */
  @Override
  public ProductDisplayDTO viewAProductDetail(Long productId) throws CosmeticException {
    var productDisplayDTO = productService.getByProductId(productId);
    var product = productDisplayDTO.getProduct();
    // check for hidden product
    if (product.getProductStatus().equals(Constants.PRODUCT_HIDDEN)) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_HAS_BEEN_HIDDEN));
    }
    // increase view when customers view the product
    var view = product.getCountView() + 1;
    product.setCountView(view);
    productRepository.save(product);
    return productDisplayDTO;
  }


}
