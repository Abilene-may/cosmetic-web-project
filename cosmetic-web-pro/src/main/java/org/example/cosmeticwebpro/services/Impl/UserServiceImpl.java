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
import org.example.cosmeticwebpro.mapper.MapStruct;
import org.example.cosmeticwebpro.models.UserDTO;
import org.example.cosmeticwebpro.models.request.CreateUserReqDTO;
import org.example.cosmeticwebpro.models.request.ResetPasswordReqDTO;
import org.example.cosmeticwebpro.models.request.UserReqDTO;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.CartService;
import org.example.cosmeticwebpro.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final MapStruct mapStruct;
  private final CartService cartService;

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

  @Transactional
  @Override
  public void removeRequestDeleteAccount(Long userId) throws CosmeticException {
    var user = this.viewDetailAUser(userId);
    LocalDateTime today = LocalDateTime.now();
    user.setRequestDate(today);
    user.setAccountStatus(Constants.PENDING_DELETION);
  }

  // create a new user for admin
  @Override
  public void createAUserForAdmin(CreateUserReqDTO reqDTO) throws CosmeticException {
    if (reqDTO.getEmail().isBlank()
        || reqDTO.getUserName().isBlank()
        || reqDTO.getAccountStatus().isBlank()
        || reqDTO.getPassword().isBlank()
        || reqDTO.getRoleId() == null) {
      throw new CosmeticException(
          ExceptionUtils.USER_REQ_NOT_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.USER_REQ_NOT_EMPTY));
    }
    // check email or username exist
    var userName =
        userRepository.findByUserNameOrEmail(reqDTO.getUserName(), reqDTO.getUserName());
    if (userName.isPresent()) {
      throw new CosmeticException(
          ExceptionUtils.USERNAME_HAS_ALREADY,
          ExceptionUtils.messages.get(ExceptionUtils.USERNAME_HAS_ALREADY));
    }
    var user = mapStruct.mapToUser(reqDTO);
    user.setPassword(passwordEncoder.encode(reqDTO.getPassword()) );
    var saveUser = userRepository.save(user);
    cartService.createCart(saveUser);
  }

  // reset password of the user for admin
  @Transactional
  @Override
  public User resetPasswordOfUserForAdmin(ResetPasswordReqDTO reqDTO) throws CosmeticException {
    if (!reqDTO.getNewPassword().equals(reqDTO.getReconfirmPassword())) {
      throw new CosmeticException(
          ExceptionUtils.PASSWORD_NOT_MATCH,
          ExceptionUtils.messages.get(ExceptionUtils.PASSWORD_NOT_MATCH));
    }
    var user = this.viewDetailAUser(reqDTO.getUserId());
    user.setPassword(passwordEncoder.encode(reqDTO.getNewPassword()));
    user.setModifiedDate(LocalDateTime.now());
    userRepository.save(user);
    return user;
  }

  // delete a user
  @Transactional
  @Override
  public void deleteAUser(Long userId) throws CosmeticException {
    var user = this.viewDetailAUser(userId);
    if (user.getRoleId() == 1 || user.getRoleId() == 2) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_CANNOT_BE_DELETED,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_CANNOT_BE_DELETED));
    }
    userRepository.delete(user);
  }


  // get all users for admin
  @Override
  public List<UserDTO> getAllUsersForAdmin() throws CosmeticException {
    return userRepository.findAllUsersForAdmin();
  }

  // change role user for admin
  @Override
  public void changeRoleUserForAdmin(Long userId, Long roleId) throws CosmeticException {
    userRepository.changeRoleUserForAdmin(userId, roleId);
  }

}
