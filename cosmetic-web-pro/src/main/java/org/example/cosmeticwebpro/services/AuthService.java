package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.TokenAuthDTO;
import org.example.cosmeticwebpro.models.request.LoginReqDTO;
import org.example.cosmeticwebpro.models.request.SignUpReqDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    TokenAuthDTO login(LoginReqDTO loginDto) throws CosmeticException;
    TokenAuthDTO signUp(SignUpReqDTO signupReqDto) throws Exception;

    TokenAuthDTO loginWithGoogle(OAuth2User principal);

    TokenAuthDTO refreshToken(String refreshToken) throws CosmeticException;
}
