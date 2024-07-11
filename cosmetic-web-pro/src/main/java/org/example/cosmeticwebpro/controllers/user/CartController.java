package org.example.cosmeticwebpro.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.CartReqDTO;
import org.example.cosmeticwebpro.services.CartService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/cart")
public class CartController {
    private final CartService cartService;

  /**
   * get all product in a cart
   */
  @GetMapping("/get-all/{cartId}")
  public ResponseEntity<Object> getAll(@PathVariable Long cartId){
    try{
      var cartLines = cartService.getAllCartLineByCartId(cartId);
      return new ResponseEntity<>(cartLines, HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * add a new product in a cart
   */
  @PostMapping("/add-a-new-product")
  public ResponseEntity<Object> addANewProduct(@RequestBody CartReqDTO cartReqDTO){
    try{
      var cartDetail = cartService.addANewProduct(cartReqDTO);
      return new ResponseEntity<>(cartDetail, HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * update
   */
  @PutMapping("/update-cart")
  public ResponseEntity<Object> updateCart(@RequestBody CartReqDTO cartReqDTO){
    try{
      var cartDetail = cartService.updateCart(cartReqDTO);
      return new ResponseEntity<>(cartDetail, HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * delete a product in the cart
   */
  @DeleteMapping("/delete-a-product")
  public ResponseEntity<Object> deleteAProduct(@RequestParam Long productId, @RequestParam Long cartId){
    try{
      cartService.deleteAProduct(productId, cartId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * delete a product in the cart
   */
  @PostMapping("/clear/{userId}")
  public ResponseEntity<Object> clearCartLine(@PathVariable Long userId){
    try{
      cartService.clearCartLine(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
