package org.example.cosmeticwebpro.controllers;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.services.HomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
  private final HomeService homeService;

  /**
   * API home screen
   * @return homeDisplayDTO
   */
  @GetMapping("/{userId}")
  public ResponseEntity<Object> displayHomeScreen(){
    try {
      var homeDisplayDTO = homeService.displayHomeScreen();
      return new ResponseEntity<>(homeDisplayDTO, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * API home screen
   * @return homeDisplayDTO
   */
  @GetMapping("/view-a-product")
  public ResponseEntity<Object> displayProductDetail(@PathVariable Long productId){
    try {
      var productDisplayDTO = homeService.viewAProductDetail(productId);
      return new ResponseEntity<>(productDisplayDTO, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
