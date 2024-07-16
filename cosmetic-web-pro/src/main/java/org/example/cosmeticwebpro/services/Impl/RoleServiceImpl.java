package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Permission;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.mapper.MapStruct;
import org.example.cosmeticwebpro.repositories.PermissionRepository;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final MapStruct mapStruct;
  private final UserRepository userRepository;

  // create a new role
  @Override
  public Role createANewRole(String roleName, List<Long> permissionIds) throws CosmeticException {
    if (roleName.isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NAME_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK));
    }
    Role role =  new Role();
    if(permissionIds == null){
      role.setPermissions(new HashSet<>());
    }
    else{
      role.setPermissions(new HashSet<>(permissionRepository.findAllById(permissionIds)));
    }
    LocalDateTime today = LocalDateTime.now();
    role.setRoleName(roleName);
    role.setCreatedDate(today);
    role.setModifiedDate(today);
    return roleRepository.save(role);
  }

  // update a role
  @Transactional
  @Override
  public Role updateARole(Long roleId ,String roleName, List<Long> permissionIds) throws CosmeticException {
    // check role exist
    var checkRole = roleRepository.findRoleById(roleId);
    if (checkRole.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NOT_FOUND));
    }
    if (roleName.isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NAME_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK));
    }
    LocalDateTime today = LocalDateTime.now();
    checkRole.get().setRoleName(roleName);
    checkRole.get().setModifiedDate(today);
    if(permissionIds == null){
      checkRole.get().setPermissions(new HashSet<>());
    }
    else{
      checkRole.get().setPermissions(new HashSet<>(permissionRepository.findAllById(permissionIds)));
    }
    roleRepository.save(checkRole.get());
    return checkRole.get();
  }

  /*
  View detail a role and permission of a role
   */
  @Transactional
  @Override
  public Role viewRoleAndPermission(Long roleId) throws CosmeticException {
    var role = roleRepository.findRoleById(roleId);
    if (role.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NOT_FOUND));
    }
    return role.get();
  }

  @Transactional
  @Override
  public List<Role> getAllRoleAndPermission() throws CosmeticException {
    return roleRepository.findAll();
  }

  @Transactional
  @Override
  public void deleteARole(Long roleId) throws CosmeticException {
    // Fetch the existing new role
    var role = this.viewRoleAndPermission(roleId);
    // check if role is USER or ADMIN
    if (role.getRoleName().equals(Constants.ROLE_USER)
        || role.getRoleName().equals(Constants.ROLE_ADMIN)) {
      throw new CosmeticException(
          ExceptionUtils.CANNOT_DELETE, ExceptionUtils.messages.get(ExceptionUtils.CANNOT_DELETE));
    }
    // delete role in permission table
    for (Permission permission : role.getPermissions()) {
      permission.getRoles().remove(role);
      permissionRepository.save(permission);
    }
    // Update all users whose roles have been deleted to the USER role
    userRepository.updateRole(roleId);
    roleRepository.deleteById(roleId);
  }


  // Get all permissions
  @Override
  public List<Permission> getAllPermissions() throws CosmeticException {
    return permissionRepository.findAll();
  }
}
