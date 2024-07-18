package org.example.cosmeticwebpro.services.Impl;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.AuthUserDTO;
import org.example.cosmeticwebpro.models.TokenAuthDTO;
import org.example.cosmeticwebpro.models.request.LoginReqDTO;
import org.example.cosmeticwebpro.models.request.SignUpReqDTO;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.AuthService;
import org.example.cosmeticwebpro.services.CartService;
import org.example.cosmeticwebpro.services.CustomUserDetailsService;
import org.example.cosmeticwebpro.services.EmailService;
import org.example.cosmeticwebpro.utils.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final CartService cartService;
  private final RoleRepository roleRepository;
  private final EmailService emailService;

  @Override
  public TokenAuthDTO login(LoginReqDTO loginDto) throws CosmeticException {
    Authentication authentication =
        authenticate(loginDto.getUsernameOrEmail(), loginDto.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    var user =
        userRepository.findByUserNameOrEmail(
            loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
    if (user.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.USER_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.USER_NOT_FOUND));
    }
    // check status account
    if (Constants.DE_ACTIVE.equals(user.get().getAccountStatus())) {
      throw new CosmeticException(
          ExceptionUtils.ACCOUNT_DEACTIVATED,
          ExceptionUtils.messages.get(ExceptionUtils.ACCOUNT_DEACTIVATED));
    }
    if (user.get().getRequestDate() != null) {
      LocalDateTime requestDate = user.get().getRequestDate();
      LocalDateTime today = LocalDateTime.now();
      long daysBetween = Duration.between(requestDate, today).toDays();
      if (daysBetween >= 30) {
        user.get().setAccountStatus(Constants.DE_ACTIVE);
        userRepository.save(user.get()); // Save the updated user status to the database
        throw new CosmeticException(
            ExceptionUtils.ACCOUNT_DEACTIVATED,
            ExceptionUtils.messages.get(ExceptionUtils.ACCOUNT_DEACTIVATED));
      }
    }
    var cart = cartService.getCartByUserId(user.get().getId());
    String token = jwtTokenProvider.generateToken(authentication);
    String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(), authentication);
    TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
    tokenAuthDTO.setAccessToken(token);
    tokenAuthDTO.setRefreshToken(refreshToken);
    tokenAuthDTO.setUser(user.get());
    tokenAuthDTO.setCart(cart);
    return tokenAuthDTO;
  }

  @Override
  public TokenAuthDTO signUp(SignUpReqDTO signupReqDto) throws Exception {
    // check null fields
    if (signupReqDto.getEmail().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.SIGNUP_ERROR_NULL_1,
          ExceptionUtils.messages.get(ExceptionUtils.SIGNUP_ERROR_NULL_1));
    }
    if (signupReqDto.getPassword().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.SIGNUP_ERROR_NULL_2,
          ExceptionUtils.messages.get(ExceptionUtils.SIGNUP_ERROR_NULL_2));
    }
    var checkExistUser = userRepository.findUserByEmail(signupReqDto.getEmail(), Constants.ACTIVE);
    // case email or username has been registered
    if (checkExistUser.isPresent())
      throw new CosmeticException(
          ExceptionUtils.USER_SIGNUP_1, ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1));
    // setup username for user
    String userName = signupReqDto.getEmail().split("@")[0];
    // setup role for user
    var role = roleRepository.findRoleByRoleName(Constants.ROLE_USER);
    // set user in DB
    User user =
        User.builder()
            .email(signupReqDto.getEmail())
            .firstName(signupReqDto.getFirstName())
            .lastName(signupReqDto.getLastName())
            .userName(userName)
            .password(passwordEncoder.encode(signupReqDto.getPassword()))
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .accountStatus(Constants.ACTIVE)
            .roleId(role.get().getId())
            .build();

    User savedUSer = userRepository.save(user);
    // create a shopping cart
    cartService.createCart(savedUSer);
    Authentication authentication =
        authenticate(savedUSer.getUserName(), signupReqDto.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.generateToken(authentication);
    String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(), authentication);
    TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
    tokenAuthDTO.setAccessToken(token);
    tokenAuthDTO.setRefreshToken(refreshToken);

    return tokenAuthDTO;
  }

  @Override
  public TokenAuthDTO loginWithGoogle(OAuth2User principal) {
    // Setup user and cart
    return new TokenAuthDTO();
  }

  @Override
  public TokenAuthDTO refreshToken(String refreshToken) throws CosmeticException {
    TokenAuthDTO tokenAuthDTO = new TokenAuthDTO();
    String userName = jwtTokenProvider.getUsername(refreshToken);
    //        Optional<Users> user = userRepository.findByUserNameOrEmail(userName,userName);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    if (jwtTokenProvider.validateToken(refreshToken) && authentication != null) {
      String jwt = jwtTokenProvider.generateToken(authentication);
      tokenAuthDTO.setRefreshToken(refreshToken);
      tokenAuthDTO.setAccessToken(jwt);
    }
    return tokenAuthDTO;
  }

  @Override
  public AuthUserDTO findUserByJwt(String jwt) throws CosmeticException {
    String userName = jwtTokenProvider.getUsername(jwt.substring(7));
    //        ============== DEMO Spring Mapstruct ==========================
    var user = userRepository.findByUserNameOrEmail(userName, userName);
    var cart = cartService.getCartByUserId(user.get().getId());
    return AuthUserDTO.builder().user(user.get()).cart(cart).build();
  }

  public String forgetPassword(String email) throws CosmeticException {
    Optional<User> user = userRepository.findByUserNameOrEmail(email, email);
    if (user.isPresent()) {
      // Generate a random alphanumeric string of length 8
      String newPassword = RandomStringUtils.randomAlphanumeric(8);

      user.get().setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user.get());

      Context context = new Context();
      context.setVariable("user_data", user.get());
      context.setVariable("password", newPassword);

      emailService.sendEmailWithHtmlTemplate(
          user.get().getEmail(), "Reset Password", "reset-password-template", context);
      return "Email sent";
    }
    throw new CosmeticException(
        ExceptionUtils.EMAIL_IS_NOT_REGISTERED,
        ExceptionUtils.messages.get(ExceptionUtils.EMAIL_IS_NOT_REGISTERED));
  }

  private Authentication authenticate(String username, String password) throws CosmeticException {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid Username");
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
