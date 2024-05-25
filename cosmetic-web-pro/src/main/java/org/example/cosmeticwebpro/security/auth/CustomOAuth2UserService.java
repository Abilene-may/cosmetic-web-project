package org.example.cosmeticwebpro.security.auth;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.AuthProvider;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.OAuth2AuthenticationProcessingException;
import org.example.cosmeticwebpro.repositories.CartRepository;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.security.UserPrincipal;
import org.example.cosmeticwebpro.security.auth.user.OAuth2UserInfo;
import org.example.cosmeticwebpro.security.auth.user.OAuth2UserInfoFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final RoleRepository roleRepository;


  @Override
  public OAuth2User loadUser(
      OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<User> userOptional = userRepository.findByUserNameOrEmail(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getEmail());
    User user;
    if(userOptional.isPresent()) {
      user = userOptional.get();
      if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
            user.getProvider() + " account. Please use your " + user.getProvider() +
            " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }
//         return userDetailsService.loadUserByUsername(oAuth2UserInfo.getEmail());

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    User user = new User();
    var role = roleRepository.findRoleByRoleName(Constants.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Role not found"));
    user.setCreatedDate(LocalDateTime.now());
    user.setRoleId(role.getId());
    user.setProviderId(oAuth2UserInfo.getId());
    user.setUserName(oAuth2UserInfo.getName());
    user.setEmail(oAuth2UserInfo.getEmail());
    user.setProvider(AuthProvider.google);
    User userSaved = userRepository.save(user);
    Cart cart = new Cart();
    cart.setUser(userSaved);
    cartRepository.save(cart);
    return userSaved;
  }

  private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    existingUser.setUserName(oAuth2UserInfo.getName());
//        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    return userRepository.save(existingUser);
  }

}