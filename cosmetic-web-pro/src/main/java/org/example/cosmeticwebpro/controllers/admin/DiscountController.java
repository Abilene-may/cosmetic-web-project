package org.example.cosmeticwebpro.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.DiscountReqDTO;
import org.example.cosmeticwebpro.models.request.DiscountUpdateReqDTO;
import org.example.cosmeticwebpro.services.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/discount")
public class DiscountController {
  private final DiscountService discountService;

  /** API get all discounts */
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAllDiscount() {
    try {
      var allDiscounts = discountService.getAllDiscounts();
      return new ResponseEntity<>(allDiscounts, HttpStatus.OK);
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

  /** API get all discounts */
  @GetMapping("/get-all-for-product")
  public ResponseEntity<Object> getAllDiscountsForProduct() {
    try {
      var allDiscounts = discountService.findAllDiscountForProduct();
      return new ResponseEntity<>(allDiscounts, HttpStatus.OK);
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

  /** create a new discount */
  @PostMapping("/create")
  public ResponseEntity<Object> createADiscount(@RequestBody DiscountReqDTO reqDTO) {
    try {
      discountService.createADiscount(reqDTO);
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

  /** update a new discount */
  @PutMapping("/update")
  public ResponseEntity<Object> updateADiscount(
      @RequestBody DiscountUpdateReqDTO reqDTO) {
    try {
      discountService.updateADiscount(reqDTO);
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

  /** update a new discount */
  @DeleteMapping("/delete/{discountId}")
  public ResponseEntity<Object> deleteDiscount(
      @PathVariable Long discountId) {
    try {
      discountService.deleteDiscountById(discountId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
