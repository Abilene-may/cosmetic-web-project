package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import org.example.cosmeticwebpro.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUserNameOrEmail(String username, String email);

    @Query(value =
            "SELECT * FROM users " +
                    "WHERE LOWER(email) LIKE LOWER(CONCAT('%', :email, '%')) " +
                    "AND account_status = :status",
            nativeQuery = true)
    Optional<User> findUserByEmail(String email, String status);


  @Query(
      value =
          "UPDATE users \n"
              + "SET role_id = 'USER' \n"
              + "WHERE role_id = :roleId ",
      nativeQuery = true)
  @Modifying
  @Transactional
  void updateRole(Long roleId);

    Optional<User> findUserByUserName(String userName);
}
