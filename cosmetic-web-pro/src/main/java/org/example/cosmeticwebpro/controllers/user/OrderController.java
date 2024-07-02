package org.example.cosmeticwebpro.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.AddressReqDTO;
import org.example.cosmeticwebpro.models.request.OrderReqDTO;
import org.example.cosmeticwebpro.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  /**
   * get all product in a cart
   */
  @GetMapping("/get-all/{userId}")
  public ResponseEntity<Object> getAll(@PathVariable Long userId){
    try{
      var orders = orderService.getAllOrderForAUser(userId);
      return new ResponseEntity<>(orders, HttpStatus.OK);
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
   * display detail an order
   */
  @GetMapping("/show-order-detail/{orderId}")
  public ResponseEntity<Object> displayDetailAnOrder(@PathVariable Long orderId){
    try{
      var orderDetailDTO = orderService.showDetailAnOrder(orderId);
      return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
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
   * create a new order
   */
  @PostMapping("/create")
  public ResponseEntity<Object> createAnAddress(@RequestBody OrderReqDTO orderReqDTO){
    try{
      var order =
          orderService.createAnOrder(
              orderReqDTO.getUserId(), orderReqDTO.getAddress(), orderReqDTO.getDiscountId());
      return new ResponseEntity<>(order, HttpStatus.OK);
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
