package org.example.cosmeticwebpro.controllers;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.models.TokenAuthDTO;
import org.example.cosmeticwebpro.models.request.LoginReqDTO;
import org.example.cosmeticwebpro.models.request.SignUpReqDTO;
import org.example.cosmeticwebpro.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDTO signupDto) {
        try {
            TokenAuthDTO jwtAuthDto = authService.signUp(signupDto);
            return new ResponseEntity<>(jwtAuthDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/refreshtoken")
    ResponseEntity<?> refreshToken(@RequestParam String token){
        try {
            TokenAuthDTO jwtAuthDto = authService.refreshToken(token);
            return new ResponseEntity<>(jwtAuthDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
