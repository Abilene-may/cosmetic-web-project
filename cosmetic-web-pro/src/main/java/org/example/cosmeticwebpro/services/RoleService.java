package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayRoleDTO;
import org.example.cosmeticwebpro.models.request.RoleAndPermissionReqDTO;
import org.example.cosmeticwebpro.models.request.UpdateRoleReqDTO;

public interface RoleService {
  void createANewRole(RoleAndPermissionReqDTO reqDTO) throws CosmeticException;

  void updateARole(UpdateRoleReqDTO reqDTO) throws CosmeticException;

  DisplayRoleDTO viewRoleAndPermission(Long roleId) throws CosmeticException;

  List<DisplayRoleDTO> getAllRoleAndPermission() throws CosmeticException;

  void deleteARole(Long roleId) throws CosmeticException;
}
