package org.example.cosmeticwebpro.repositories;

import java.util.Optional;
import org.example.cosmeticwebpro.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Long> {
    Optional<Role> findRoleByRoleName(String roleName);
}
