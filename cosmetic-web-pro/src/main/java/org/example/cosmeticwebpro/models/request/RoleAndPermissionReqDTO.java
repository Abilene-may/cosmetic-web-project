package org.example.cosmeticwebpro.models.request;

import java.util.List;
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
  private String roleName;

  private List<Long> permissionIds;
}
