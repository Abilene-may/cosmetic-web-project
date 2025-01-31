package org.example.cosmeticwebpro.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.ProductReqDTO;
import org.example.cosmeticwebpro.models.request.ProductUpdateReqDTO;
import org.example.cosmeticwebpro.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  /** Create a product */
  @PostMapping("/create")
  public ResponseEntity<Object> createProduct(
      @RequestParam("title") String title,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam("currentCost") double currentCost,
      @RequestParam("madeIn") String madeIn,
      @RequestParam(value = "capacity", required = false) Integer capacity,
      @RequestParam("quantity") Integer quantity,
      @RequestParam(value = "productStatus", required = false) String productStatus,
      @RequestParam(required = false) MultipartFile[] multipartFiles,
      @RequestParam(value = "brandId", required = false) Long brandId,
      @RequestParam(value = "discountId", required = false ) Long discountId,
      @RequestParam(value = "categoryId", required = false) Long categoryId) {
    try {
      var productReqDTO =
          ProductReqDTO.builder()
              .title(title)
              .description(description)
              .currentCost(currentCost)
              .madeIn(madeIn)
              .capacity(capacity)
              .quantity(quantity)
              .productStatus(productStatus)
              .brandId(brandId)
              .discountId(discountId)
              .categoryId(categoryId)
              .build();
      productService.createProduct(productReqDTO, multipartFiles);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * API show detail information for 1 product
   *
   * @param productId
   * @return
   */
  @GetMapping("/get-by-id")
  public ResponseEntity<Object> getProductById(@RequestParam Long productId) {
    try {
      var product = productService.getByProductId(productId);
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** API update all product status */
  @PutMapping("/update-all-product-status")
  private ResponseEntity<Object> updateAllProductStatus() {
    try {
      var productList = productService.updateAllProductStatus();
      return new ResponseEntity<>(productList, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** update a product */
  @PutMapping("/update")
  public ResponseEntity<Object> updateProduct(
      @RequestParam("id") Long id,
      @RequestParam("title") String title,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam("currentCost") double currentCost,
      @RequestParam("madeIn") String madeIn,
      @RequestParam(value = "capacity", required = false) Integer capacity,
      @RequestParam("quantity") Integer quantity,
      @RequestParam(value = "productStatus", required = false) String productStatus,
      @RequestParam(required = false) MultipartFile[] multipartFiles,
      @RequestParam(required = false) Long[] imageIdDelete,
      @RequestParam(value = "brandId", required = false) Long brandId,
      @RequestParam(value = "discountId", required = false) Long discountId,
      @RequestParam(value = "categoryId", required = false) Long categoryId) {
    var reqDTO =
        ProductUpdateReqDTO.builder()
            .id(id)
            .title(title)
            .description(description)
            .currentCost(currentCost)
            .madeIn(madeIn)
            .capacity(capacity)
            .quantity(quantity)
            .productStatus(productStatus)
            .brandId(brandId)
            .discountId(discountId)
            .categoryId(categoryId)
            .build();
    try {
      productService.updateProduct(reqDTO, multipartFiles, imageIdDelete);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** API get all products for admin */
  @GetMapping("/get-all")
  public ResponseEntity<Object> displayProductForAdmin() {
    try {
      var product = productService.getAllProductForAdmin();
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
