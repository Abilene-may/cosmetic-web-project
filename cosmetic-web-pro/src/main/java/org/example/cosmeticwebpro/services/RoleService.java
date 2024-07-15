package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Permission;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.exceptions.CosmeticException;

public interface RoleService {
  Role createANewRole(String roleName, List<Long> permissionIds) throws CosmeticException;

  Role updateARole(Long roleId, String roleName, List<Long> permissionIds) throws CosmeticException;

  Role viewRoleAndPermission(Long roleId) throws CosmeticException;

  List<Role> getAllRoleAndPermission() throws CosmeticException;

  void deleteARole(Long roleId) throws CosmeticException;

  List<Permission> getAllPermissions() throws CosmeticException;
}
