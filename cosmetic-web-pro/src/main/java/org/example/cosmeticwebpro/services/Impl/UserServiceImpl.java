package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.commons.Constants;
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
          ExceptionUtils.USER_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.USER_NOT_FOUND));
    }
    return user.get();
  }

  /*
  update a user
   */
  @Transactional
  @Override
  public User updateUser(UserReqDTO userReqDTO) throws CosmeticException {
    var updateUser = this.viewDetailAUser(userReqDTO.getUserId());
    updateUser.setFirstName(userReqDTO.getFirstName());
    updateUser.setLastName(userReqDTO.getLastName());
    if (!updateUser.getUserName().equals(userReqDTO.getUserName())) {
      // check userName existed
      var userName =
          userRepository.findByUserNameOrEmail(userReqDTO.getUserName(), userReqDTO.getUserName());
      if (userName.isPresent()) {
        throw new CosmeticException(
            ExceptionUtils.USERNAME_HAS_ALREADY,
            ExceptionUtils.messages.get(ExceptionUtils.USERNAME_HAS_ALREADY));
      }
    }
    updateUser.setUserName(userReqDTO.getUserName());
    if (!userReqDTO.getPassword().isBlank() || !userReqDTO.getPassword().isEmpty()) {
      updateUser.setPassword(passwordEncoder.encode(userReqDTO.getPassword()));
    }
    updateUser.setModifiedDate(LocalDateTime.now());
    // request delete account
    if (userReqDTO.getRequestDate() != null) {
      updateUser.setRequestDate(userReqDTO.getRequestDate());
    }
    userRepository.save(updateUser);
    return updateUser;
  }

  @Override
  public List<User> getAll() throws CosmeticException {
    return userRepository.findAll();
  }

  @Override
  public void requestDeleteAccount(Long userId) throws CosmeticException {}

  @Transactional
  @Override
  public void removeRequestDeleteAccount(Long userId) throws CosmeticException {
    var user = this.viewDetailAUser(userId);
    LocalDateTime today = LocalDateTime.now();
    user.setRequestDate(today);
    user.setAccountStatus(Constants.PENDING_DELETION);
  }
}
