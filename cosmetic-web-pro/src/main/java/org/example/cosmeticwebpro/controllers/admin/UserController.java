package org.example.cosmeticwebpro.controllers.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.CreateUserReqDTO;
import org.example.cosmeticwebpro.models.request.ResetPasswordReqDTO;
import org.example.cosmeticwebpro.models.request.UserReqDTO;
import org.example.cosmeticwebpro.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  /**
   * API show detail a user
   */
  @GetMapping("/view-detail/{userId}")
  public ResponseEntity<Object> viewDetailUser(@PathVariable Long userId){
    try {
      var user = userService.viewDetailAUser(userId);
      return new ResponseEntity<>(user, HttpStatus.OK);
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
   * API show detail a user
   */
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll(){
    try {
      var userList = userService.getAll();
      return new ResponseEntity<>(userList, HttpStatus.OK);
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
   * update a user
   */
  @PutMapping("/update")
  public ResponseEntity<Object> updateUser(@RequestBody UserReqDTO userReqDTO){
    try{
      var user = userService.updateUser(userReqDTO);
      return new ResponseEntity<>(user, HttpStatus.OK);
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
   * request delete account
   */
  @PutMapping("/request-delete/{userId}")
  public ResponseEntity<Object> updateUser(@PathVariable Long userId){
    try{
      userService.removeRequestDeleteAccount(userId);
      return new ResponseEntity<>(HttpStatus.OK);
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
   * create a user for admin
   */
  @PostMapping("/admin/create-user")
  public ResponseEntity<Object> createUserForAdmin(@RequestBody CreateUserReqDTO reqDTO){
    try{
      userService.createAUserForAdmin(reqDTO);
      return new ResponseEntity<>(HttpStatus.OK);
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
   * reset password of user for admin
   */
  @PostMapping("/admin/reset-password")
  public ResponseEntity<Object> resetPasswordOfUserForAdmin(@RequestBody ResetPasswordReqDTO reqDTO){
    try{
      var user = userService.resetPasswordOfUserForAdmin(reqDTO);
      return new ResponseEntity<>(user, HttpStatus.OK);
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
