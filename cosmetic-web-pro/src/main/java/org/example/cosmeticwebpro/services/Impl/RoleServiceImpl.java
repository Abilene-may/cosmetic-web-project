package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Permission;
import org.example.cosmeticwebpro.domains.Role;
import org.example.cosmeticwebpro.domains.RolePermission;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.mapper.MapStruct;
import org.example.cosmeticwebpro.models.DisplayRoleDTO;
import org.example.cosmeticwebpro.models.request.RoleAndPermissionReqDTO;
import org.example.cosmeticwebpro.models.request.UpdateRoleReqDTO;
import org.example.cosmeticwebpro.repositories.PermissionRepository;
import org.example.cosmeticwebpro.repositories.RolePermissionRepository;
import org.example.cosmeticwebpro.repositories.RoleRepository;
import org.example.cosmeticwebpro.repositories.UserRepository;
import org.example.cosmeticwebpro.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;
  private final MapStruct mapStruct;
  private final UserRepository userRepository;

  // create a new role
  @Override
  public void createANewRole(RoleAndPermissionReqDTO reqDTO) throws CosmeticException {
    if (reqDTO.getRoleName().isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NAME_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK));
    }
    LocalDateTime today = LocalDateTime.now();
    Role role =
        Role.builder()
            .roleName(reqDTO.getRoleName())
            .createdDate(today)
            .modifiedDate(today)
            .build();
    var saveRole = roleRepository.save(role);
    for (Long permissionId : reqDTO.getPermissionIds()) {
      roleRepository.createPermissionToRole(saveRole.getId(), permissionId);
    }
  }

  // update a role
  @Override
  public void updateARole(UpdateRoleReqDTO reqDTO) throws CosmeticException {
    // check role exist
    var checkRole = roleRepository.findRoleById(reqDTO.getRole().getId());
    if (checkRole.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NOT_FOUND));
    }
    var roleName = reqDTO.getRole().getRoleName();
    if (roleName.isBlank()) {
      throw new CosmeticException(
          ExceptionUtils.ROLE_NAME_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.ROLE_NAME_IS_NOT_BLANK));
    }
    LocalDateTime today = LocalDateTime.now();
    Role role = Role.builder().roleName(roleName).createdDate(today).modifiedDate(today).build();
    var saveRole = roleRepository.save(role);
    for (Permission permission : reqDTO.getPermissions()) {
      roleRepository.createPermissionToRole(saveRole.getId(), permission.getId());
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
    var permissions = permissionRepository.findAllByRoleId(roleId);

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
      var permissions = permissionRepository.findAllByRoleId(role.getId());
      displayRoleDTO.setPermissions(permissions);
      displayRoleDTOs.add(displayRoleDTO);
    }
    return displayRoleDTOs;
  }

  @Transactional
  @Override
  public void deleteARole(Long roleId) throws CosmeticException {
    // Fetch the existing new role
    var role = this.getById(roleId);
    // check if role is USER or ADMIN
    if (role.getRoleName().equals(Constants.ROLE_USER)
        || role.getRoleName().equals(Constants.ROLE_ADMIN)) {
      throw new CosmeticException(
          ExceptionUtils.CANNOT_DELETE, ExceptionUtils.messages.get(ExceptionUtils.CANNOT_DELETE));
    }
    // Update all users whose roles have been deleted to the USER role
    userRepository.updateRole(roleId);
    roleRepository.delete(role);
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
