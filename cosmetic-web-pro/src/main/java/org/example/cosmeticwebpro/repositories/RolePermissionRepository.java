package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Set;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>,
    JpaSpecificationExecutor<Long> {
  Set<RolePermission> findByRoleId(Long roleId);

}
