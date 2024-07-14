package org.example.cosmeticwebpro.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.TokenAuthDTO;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.LoginReqDTO;
import org.example.cosmeticwebpro.models.request.SignUpReqDTO;
import org.example.cosmeticwebpro.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
  // EXAMPLE CONSTRUCTOR-BASED INJECTION
  private final AuthService authService;

  //    private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReqDTO loginDto) {
    try {
      TokenAuthDTO jwtAuthDto = authService.login(loginDto);
      return new ResponseEntity<>(jwtAuthDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody SignUpReqDTO signupDto) {
    try {
      TokenAuthDTO jwtAuthDto = authService.signUp(signupDto);
      return new ResponseEntity<>(jwtAuthDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/refreshtoken")
  ResponseEntity<?> refreshToken(@RequestParam String token) {
    try {
      TokenAuthDTO jwtAuthDto = authService.refreshToken(token);
      return new ResponseEntity<>(jwtAuthDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /** get user and cart by jwt */
  @GetMapping("/profile")
  public ResponseEntity<Object> getUSerProfileHandler(
      @RequestHeader(value = "Authorization") String jwt) {
    try {
      var authUserDTO = authService.findUserByJwt(jwt);
      return new ResponseEntity<>(authUserDTO, HttpStatus.OK);
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

  /** get user and cart by jwt */
  @PostMapping("/forget-password")
  public ResponseEntity<Object> forgetPassword(@RequestParam String email) {
    try {
      var forgetPassword = authService.forgetPassword(email);
      return new ResponseEntity<>(forgetPassword, HttpStatus.OK);
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
