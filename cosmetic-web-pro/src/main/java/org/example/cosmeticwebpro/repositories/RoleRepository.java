package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.example.cosmeticwebpro.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Long> {


    @Modifying
    @Query(value = "INSERT INTO role_permission (role_id, permission_id) "
        + " VALUES (:roleId, :permissionId)", nativeQuery = true)
    @Transactional
    void createPermissionToRole(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

    Optional<Role> findRoleByRoleName(String roleName);

    Optional<Role> findRoleById(Long roleId);
}
