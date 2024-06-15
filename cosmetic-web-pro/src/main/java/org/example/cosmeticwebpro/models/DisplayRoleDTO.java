package org.example.cosmeticwebpro.models;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Permission;
import org.example.cosmeticwebpro.domains.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisplayRoleDTO {
  private Role role;

  private Set<Permission> permissions;
}
