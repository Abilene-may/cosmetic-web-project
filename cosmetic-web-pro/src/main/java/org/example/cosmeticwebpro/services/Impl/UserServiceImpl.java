package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.UserReqDTO;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /*
  view a user detail
   */
  @Override
  public User viewDetailAUser(Long userId) throws CosmeticException {
    var user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.USER_IS_NOT_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.USER_IS_NOT_EMPTY));
    }
    return user.get();
  }

  /*
  update a user
   */
  @Transactional
  @Override
  public void updateUser(UserReqDTO userReqDTO) throws CosmeticException {
    this.viewDetailAUser(userReqDTO.getUserId());
    if (userReqDTO.getPassword().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.SIGNUP_ERROR_NULL_2,
          ExceptionUtils.messages.get(ExceptionUtils.SIGNUP_ERROR_NULL_2));
    }
    // check userName existed
    var userByUserName = userRepository.findUserByUserName(userReqDTO.getUserName());
    if (userByUserName.isPresent()) {
      throw new CosmeticException(
          ExceptionUtils.USERNAME_HAS_ALREADY,
          ExceptionUtils.messages.get(ExceptionUtils.USERNAME_HAS_ALREADY));
    }
    User updateUser = new User();
    updateUser.setFirstName(userReqDTO.getFirstName());
    updateUser.setLastName(userReqDTO.getLastName());
    updateUser.setUserName(userReqDTO.getUserName());
    updateUser.setPassword(passwordEncoder.encode(userReqDTO.getPassword()));
    updateUser.setModifiedDate(LocalDateTime.now());
    // request delete account
    if (userReqDTO.getRequestDate() != null) {
      updateUser.setRequestDate(userReqDTO.getRequestDate());
    }
    userRepository.save(updateUser);
  }

  @Override
  public void requestDeleteAccount(Long userId) throws CosmeticException {}

  @Override
  public void removeRequestDeleteAccount(Long userId) throws CosmeticException {}
}
