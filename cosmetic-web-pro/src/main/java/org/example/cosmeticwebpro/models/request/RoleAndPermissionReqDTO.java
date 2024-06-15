package org.example.cosmeticwebpro.models.request;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Permission;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndPermissionReqDTO {
  private Long roleId;

  private String roleName;

  private Set<Permission> permissions;
}
