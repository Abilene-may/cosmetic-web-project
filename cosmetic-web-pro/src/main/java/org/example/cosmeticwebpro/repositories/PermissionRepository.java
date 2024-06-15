package org.example.cosmeticwebpro.repositories;


import java.util.Set;
import org.example.cosmeticwebpro.domains.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface PermissionRepository extends JpaRepository<Permission, Long>,
    JpaSpecificationExecutor<Long> {
  @Query(value = "SELECT p.* FROM permission p " +
      " INNER JOIN role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = :roleId "
      , nativeQuery = true)
  Set<Permission> findAllByRoleId(@Param("roleId") Long roleId);
}
