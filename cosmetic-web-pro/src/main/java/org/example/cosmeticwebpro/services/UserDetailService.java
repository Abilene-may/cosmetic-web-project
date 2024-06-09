package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.UserReqDTO;

public interface UserDetailService {
  User viewDetailAUser(Long userId) throws CosmeticException;

  void updateUser(UserReqDTO userReqDTO) throws CosmeticException;

  void requestDeleteAccount(Long userId) throws CosmeticException;

  void removeRequestDeleteAccount(Long userId) throws CosmeticException;
}
