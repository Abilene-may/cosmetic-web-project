package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.UserReqDTO;

public interface UserService {
  User viewDetailAUser(Long userId) throws CosmeticException;

  User updateUser(UserReqDTO userReqDTO) throws CosmeticException;

  List<User> getAll() throws CosmeticException;

  void requestDeleteAccount(Long userId) throws CosmeticException;

  void removeRequestDeleteAccount(Long userId) throws CosmeticException;
}
