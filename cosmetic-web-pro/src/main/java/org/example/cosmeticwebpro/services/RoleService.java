package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayRoleDTO;
import org.example.cosmeticwebpro.models.request.RoleAndPermissionReqDTO;

public interface RoleService {
  void createARoleAndPermissions(RoleAndPermissionReqDTO reqDTO) throws CosmeticException;

  void updateRoleOrPermission(RoleAndPermissionReqDTO reqDTO) throws CosmeticException;

  DisplayRoleDTO viewRoleAndPermission(Long roleId) throws CosmeticException;

  List<DisplayRoleDTO> getAllRoleAndPermission() throws CosmeticException;

  void changeRole(Long oldRoleId, Long newRoleId) throws CosmeticException;
}
