package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
    // check userName existed
    updateUser.setFirstName(userReqDTO.getFirstName());
    updateUser.setLastName(userReqDTO.getLastName());
    updateUser.setUserName(userReqDTO.getUserName());
    if (!userReqDTO.getPassword().isBlank()) {
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

  @Override
  public void removeRequestDeleteAccount(Long userId) throws CosmeticException {}
}
