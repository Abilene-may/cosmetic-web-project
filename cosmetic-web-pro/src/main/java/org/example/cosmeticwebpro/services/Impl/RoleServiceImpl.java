package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Permission;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.domains.RolePermission;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.mapper.MapStruct;
import org.example.cosmeticwebpro.models.DisplayRoleDTO;
import org.example.cosmeticwebpro.models.request.RoleAndPermissionReqDTO;
import org.example.cosmeticwebpro.repositories.PermissionRepository;
import org.example.cosmeticwebpro.repositories.RolePermissionRepository;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;
  private final MapStruct mapStruct;

  /*
  create a new role and permission of role
   */
  @Override
  public void createARoleAndPermissions(RoleAndPermissionReqDTO reqDTO) throws CosmeticException {
    var permissions = reqDTO.getPermissions();
    if (reqDTO.getRoleName().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NAME_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK));
    }
    LocalDateTime today = LocalDateTime.now();
    Role createRole =
        Role.builder()
            .roleName(reqDTO.getRoleName())
            .createdDate(today)
            .modifiedDate(today)
            .build();
    var role = roleRepository.save(createRole);

    for (Permission permission : permissions) {
      var checkPermission =
          permissionRepository
              .findById(permission.getId())
              .orElseThrow(() -> new RuntimeException("Permission not found"));

      RolePermission rolePermission =
          RolePermission.builder()
              .roleId(role.getId())
              .permissionId(checkPermission.getId())
              .roleId(role.getId())
              .permission(checkPermission)
              .build();

      rolePermissionRepository.save(rolePermission);
    }

    DisplayRoleDTO.builder().role(role).permissions(permissions).build();
  }

  /** update a role or permission of the role */
  @Transactional
  @Override
  public void updateRoleOrPermission(RoleAndPermissionReqDTO reqDTO) throws CosmeticException {
    // Fetch the existing role
    var updateRole = this.getById(reqDTO.getRoleId());

    // Update role name
    updateRole.setRoleName(reqDTO.getRoleName());
    roleRepository.save(updateRole);

    // Fetch existing permissions for the role
    Set<RolePermission> existingRolePermissions =
        rolePermissionRepository.findByRoleId(reqDTO.getRoleId());

    // Delete the existing RolePermission records
    rolePermissionRepository.deleteAll(existingRolePermissions);

    // Add the new RolePermission records
    for (Permission permission : reqDTO.getPermissions()) {
      var checkPermission =
          permissionRepository
              .findById(permission.getId())
              .orElseThrow(() -> new RuntimeException("Permission not found"));

      RolePermission rolePermission =
          RolePermission.builder()
              .roleId(updateRole.getId())
              .permissionId(checkPermission.getId())
              .role(updateRole)
              .permission(checkPermission)
              .build();

      rolePermissionRepository.save(rolePermission);
    }
  }

  /*
  View detail a role and permission of a role
   */
  @Transactional
  @Override
  public DisplayRoleDTO viewRoleAndPermission(Long roleId) throws CosmeticException {
    // Fetch the role by ID
    var role = this.getById(roleId);

    // Fetch the permissions related to the role
    Set<Permission> permissions = permissionRepository.findAllByRoleId(roleId);

    // Build and return the DisplayRoleDTO
    DisplayRoleDTO displayRoleDTO =
        DisplayRoleDTO.builder().role(role).permissions(permissions).build();
    return displayRoleDTO;
  }

  @Transactional
  @Override
  public List<DisplayRoleDTO> getAllRoleAndPermission() throws CosmeticException {
    // Fetch all roles
    List<Role> roles = roleRepository.findAll();
    List<DisplayRoleDTO> displayRoleDTOs = new ArrayList<>();

    for (Role role : roles) {
      var displayRoleDTO = mapStruct.mapToDisplayRoleDTO(role);
      displayRoleDTOs.add(displayRoleDTO);
    }
    return displayRoleDTOs;
  }

  @Transactional
  @Override
  public void changeRole(Long oldRoleId, Long newRoleId) throws CosmeticException {
    // Fetch the existing old role
    Role oldRole =
        roleRepository
            .findById(oldRoleId)
            .orElseThrow(() -> new CosmeticException("Old role not found"));

    // Fetch the existing new role
    Role newRole =
        roleRepository
            .findById(newRoleId)
            .orElseThrow(() -> new CosmeticException("New role not found"));

    // Fetch existing RolePermission records for the old role
    Set<RolePermission> oldRolePermissions = rolePermissionRepository.findByRoleId(oldRoleId);

    // Update RolePermission records to point to the new role
    for (RolePermission oldRolePermission : oldRolePermissions) {
      oldRolePermission.setRoleId(newRoleId);
      oldRolePermission.setRole(newRole);
      rolePermissionRepository.save(oldRolePermission);
    }

    // Delete the old role
    roleRepository.delete(oldRole);
  }

  private Role getById(Long roleId) throws CosmeticException {
    var role = roleRepository.findRoleById(roleId);
    if (role.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NOT_FOUND));
    }
    return role.get();
  }
}
